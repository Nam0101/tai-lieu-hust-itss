import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { from, of, Subject } from 'rxjs';

import { IMajor } from 'app/entities/major/major.model';
import { MajorService } from 'app/entities/major/service/major.service';
import { SubjectService } from '../service/subject.service';
import { ISubject } from '../subject.model';
import { SubjectFormService } from './subject-form.service';

import { SubjectUpdateComponent } from './subject-update.component';

describe('Subject Management Update Component', () => {
  let comp: SubjectUpdateComponent;
  let fixture: ComponentFixture<SubjectUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let subjectFormService: SubjectFormService;
  let subjectService: SubjectService;
  let majorService: MajorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, SubjectUpdateComponent],
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
      .overrideTemplate(SubjectUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SubjectUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    subjectFormService = TestBed.inject(SubjectFormService);
    subjectService = TestBed.inject(SubjectService);
    majorService = TestBed.inject(MajorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Major query and add missing value', () => {
      const subject: ISubject = { id: 456 };
      const majors: IMajor[] = [{ id: 19675 }];
      subject.majors = majors;

      const majorCollection: IMajor[] = [{ id: 3541 }];
      jest.spyOn(majorService, 'query').mockReturnValue(of(new HttpResponse({ body: majorCollection })));
      const additionalMajors = [...majors];
      const expectedCollection: IMajor[] = [...additionalMajors, ...majorCollection];
      jest.spyOn(majorService, 'addMajorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ subject });
      comp.ngOnInit();

      expect(majorService.query).toHaveBeenCalled();
      expect(majorService.addMajorToCollectionIfMissing).toHaveBeenCalledWith(
        majorCollection,
        ...additionalMajors.map(expect.objectContaining),
      );
      expect(comp.majorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const subject: ISubject = { id: 456 };
      const majors: IMajor = { id: 12909 };
      subject.majors = [majors];

      activatedRoute.data = of({ subject });
      comp.ngOnInit();

      expect(comp.majorsSharedCollection).toContain(majors);
      expect(comp.subject).toEqual(subject);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubject>>();
      const subject = { id: 123 };
      jest.spyOn(subjectFormService, 'getSubject').mockReturnValue(subject);
      jest.spyOn(subjectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subject });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subject }));
      saveSubject.complete();

      // THEN
      expect(subjectFormService.getSubject).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(subjectService.update).toHaveBeenCalledWith(expect.objectContaining(subject));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubject>>();
      const subject = { id: 123 };
      jest.spyOn(subjectFormService, 'getSubject').mockReturnValue({ id: null });
      jest.spyOn(subjectService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subject: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subject }));
      saveSubject.complete();

      // THEN
      expect(subjectFormService.getSubject).toHaveBeenCalled();
      expect(subjectService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubject>>();
      const subject = { id: 123 };
      jest.spyOn(subjectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subject });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(subjectService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMajor', () => {
      it('Should forward to majorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(majorService, 'compareMajor');
        comp.compareMajor(entity, entity2);
        expect(majorService.compareMajor).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
