import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IDocument } from 'app/entities/document/document.model';
import { DocumentService } from 'app/entities/document/service/document.service';
import { UrlService } from '../service/url.service';
import { IUrl } from '../url.model';
import { UrlFormService } from './url-form.service';

import { UrlUpdateComponent } from './url-update.component';

describe('Url Management Update Component', () => {
  let comp: UrlUpdateComponent;
  let fixture: ComponentFixture<UrlUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let urlFormService: UrlFormService;
  let urlService: UrlService;
  let documentService: DocumentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, UrlUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(UrlUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UrlUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    urlFormService = TestBed.inject(UrlFormService);
    urlService = TestBed.inject(UrlService);
    documentService = TestBed.inject(DocumentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Document query and add missing value', () => {
      const url: IUrl = { id: 456 };
      const document: IDocument = { id: 11646 };
      url.document = document;

      const documentCollection: IDocument[] = [{ id: 2875 }];
      jest.spyOn(documentService, 'query').mockReturnValue(of(new HttpResponse({ body: documentCollection })));
      const additionalDocuments = [document];
      const expectedCollection: IDocument[] = [...additionalDocuments, ...documentCollection];
      jest.spyOn(documentService, 'addDocumentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ url });
      comp.ngOnInit();

      expect(documentService.query).toHaveBeenCalled();
      expect(documentService.addDocumentToCollectionIfMissing).toHaveBeenCalledWith(
        documentCollection,
        ...additionalDocuments.map(expect.objectContaining),
      );
      expect(comp.documentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const url: IUrl = { id: 456 };
      const document: IDocument = { id: 8563 };
      url.document = document;

      activatedRoute.data = of({ url });
      comp.ngOnInit();

      expect(comp.documentsSharedCollection).toContain(document);
      expect(comp.url).toEqual(url);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUrl>>();
      const url = { id: 123 };
      jest.spyOn(urlFormService, 'getUrl').mockReturnValue(url);
      jest.spyOn(urlService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ url });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: url }));
      saveSubject.complete();

      // THEN
      expect(urlFormService.getUrl).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(urlService.update).toHaveBeenCalledWith(expect.objectContaining(url));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUrl>>();
      const url = { id: 123 };
      jest.spyOn(urlFormService, 'getUrl').mockReturnValue({ id: null });
      jest.spyOn(urlService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ url: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: url }));
      saveSubject.complete();

      // THEN
      expect(urlFormService.getUrl).toHaveBeenCalled();
      expect(urlService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUrl>>();
      const url = { id: 123 };
      jest.spyOn(urlService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ url });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(urlService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDocument', () => {
      it('Should forward to documentService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(documentService, 'compareDocument');
        comp.compareDocument(entity, entity2);
        expect(documentService.compareDocument).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
