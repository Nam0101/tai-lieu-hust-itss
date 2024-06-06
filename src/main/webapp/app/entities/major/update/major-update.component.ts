import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISubject } from 'app/entities/subject/subject.model';
import { SubjectService } from 'app/entities/subject/service/subject.service';
import { IMajor } from '../major.model';
import { MajorService } from '../service/major.service';
import { MajorFormGroup, MajorFormService } from './major-form.service';

@Component({
  standalone: true,
  selector: 'jhi-major-update',
  templateUrl: './major-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MajorUpdateComponent implements OnInit {
  isSaving = false;
  major: IMajor | null = null;

  subjectsSharedCollection: ISubject[] = [];

  protected majorService = inject(MajorService);
  protected majorFormService = inject(MajorFormService);
  protected subjectService = inject(SubjectService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MajorFormGroup = this.majorFormService.createMajorFormGroup();

  compareSubject = (o1: ISubject | null, o2: ISubject | null): boolean => this.subjectService.compareSubject(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ major }) => {
      this.major = major;
      if (major) {
        this.updateForm(major);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const major = this.majorFormService.getMajor(this.editForm);
    if (major.id !== null) {
      this.subscribeToSaveResponse(this.majorService.update(major));
    } else {
      this.subscribeToSaveResponse(this.majorService.create(major));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMajor>>): void {
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

  protected updateForm(major: IMajor): void {
    this.major = major;
    this.majorFormService.resetForm(this.editForm, major);

    this.subjectsSharedCollection = this.subjectService.addSubjectToCollectionIfMissing<ISubject>(
      this.subjectsSharedCollection,
      ...(major.subjects ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.subjectService
      .query()
      .pipe(map((res: HttpResponse<ISubject[]>) => res.body ?? []))
      .pipe(
        map((subjects: ISubject[]) =>
          this.subjectService.addSubjectToCollectionIfMissing<ISubject>(subjects, ...(this.major?.subjects ?? [])),
        ),
      )
      .subscribe((subjects: ISubject[]) => (this.subjectsSharedCollection = subjects));
  }
}
