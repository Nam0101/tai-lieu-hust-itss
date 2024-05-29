import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const major: IMajor = { id: 456 };

      activatedRoute.data = of({ major });
      comp.ngOnInit();

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
});
