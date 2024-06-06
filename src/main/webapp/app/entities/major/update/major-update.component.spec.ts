import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { from, of, Subject } from 'rxjs';

import { ISubject } from 'app/entities/subject/subject.model';
import { SubjectService } from 'app/entities/subject/service/subject.service';
import { MajorService } from '../service/major.service';
import { IMajor } from '../major.model';
import { MajorFormService } from './major-form.service';

import { MajorUpdateComponent } from './major-update.component';

describe('Major Management Update Component', () => {
  let comp: MajorUpdateComponent;
  let fixture: ComponentFixture<MajorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let majorFormService: MajorFormService;
  let majorService: MajorService;
  let subjectService: SubjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, MajorUpdateComponent],
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
      .overrideTemplate(MajorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MajorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    majorFormService = TestBed.inject(MajorFormService);
    majorService = TestBed.inject(MajorService);
    subjectService = TestBed.inject(SubjectService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Subject query and add missing value', () => {
      const major: IMajor = { id: 456 };
      const subjects: ISubject[] = [{ id: 25964 }];
      major.subjects = subjects;

      const subjectCollection: ISubject[] = [{ id: 12120 }];
      jest.spyOn(subjectService, 'query').mockReturnValue(of(new HttpResponse({ body: subjectCollection })));
      const additionalSubjects = [...subjects];
      const expectedCollection: ISubject[] = [...additionalSubjects, ...subjectCollection];
      jest.spyOn(subjectService, 'addSubjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ major });
      comp.ngOnInit();

      expect(subjectService.query).toHaveBeenCalled();
      expect(subjectService.addSubjectToCollectionIfMissing).toHaveBeenCalledWith(
        subjectCollection,
        ...additionalSubjects.map(expect.objectContaining),
      );
      expect(comp.subjectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const major: IMajor = { id: 456 };
      const subjects: ISubject = { id: 3790 };
      major.subjects = [subjects];

      activatedRoute.data = of({ major });
      comp.ngOnInit();

      expect(comp.subjectsSharedCollection).toContain(subjects);
      expect(comp.major).toEqual(major);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMajor>>();
      const major = { id: 123 };
      jest.spyOn(majorFormService, 'getMajor').mockReturnValue(major);
      jest.spyOn(majorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ major });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: major }));
      saveSubject.complete();

      // THEN
      expect(majorFormService.getMajor).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(majorService.update).toHaveBeenCalledWith(expect.objectContaining(major));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMajor>>();
      const major = { id: 123 };
      jest.spyOn(majorFormService, 'getMajor').mockReturnValue({ id: null });
      jest.spyOn(majorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ major: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: major }));
      saveSubject.complete();

      // THEN
      expect(majorFormService.getMajor).toHaveBeenCalled();
      expect(majorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMajor>>();
      const major = { id: 123 };
      jest.spyOn(majorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ major });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(majorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSubject', () => {
      it('Should forward to subjectService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(subjectService, 'compareSubject');
        comp.compareSubject(entity, entity2);
        expect(subjectService.compareSubject).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
