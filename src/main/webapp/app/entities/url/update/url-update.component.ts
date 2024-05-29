import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDocument } from 'app/entities/document/document.model';
import { DocumentService } from 'app/entities/document/service/document.service';
import { IUrl } from '../url.model';
import { UrlService } from '../service/url.service';
import { UrlFormService, UrlFormGroup } from './url-form.service';

@Component({
  standalone: true,
  selector: 'jhi-url-update',
  templateUrl: './url-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class UrlUpdateComponent implements OnInit {
  isSaving = false;
  url: IUrl | null = null;

  documentsSharedCollection: IDocument[] = [];

  protected urlService = inject(UrlService);
  protected urlFormService = inject(UrlFormService);
  protected documentService = inject(DocumentService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: UrlFormGroup = this.urlFormService.createUrlFormGroup();

  compareDocument = (o1: IDocument | null, o2: IDocument | null): boolean => this.documentService.compareDocument(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ url }) => {
      this.url = url;
      if (url) {
        this.updateForm(url);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const url = this.urlFormService.getUrl(this.editForm);
    if (url.id !== null) {
      this.subscribeToSaveResponse(this.urlService.update(url));
    } else {
      this.subscribeToSaveResponse(this.urlService.create(url));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUrl>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(url: IUrl): void {
    this.url = url;
    this.urlFormService.resetForm(this.editForm, url);

    this.documentsSharedCollection = this.documentService.addDocumentToCollectionIfMissing<IDocument>(
      this.documentsSharedCollection,
      url.document,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.documentService
      .query()
      .pipe(map((res: HttpResponse<IDocument[]>) => res.body ?? []))
      .pipe(
        map((documents: IDocument[]) => this.documentService.addDocumentToCollectionIfMissing<IDocument>(documents, this.url?.document)),
      )
      .subscribe((documents: IDocument[]) => (this.documentsSharedCollection = documents));
  }
}
