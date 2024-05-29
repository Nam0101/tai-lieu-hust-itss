import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../subject.test-samples';

import { SubjectFormService } from './subject-form.service';

describe('Subject Form Service', () => {
  let service: SubjectFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubjectFormService);
  });

  describe('Service methods', () => {
    describe('createSubjectFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSubjectFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
            major: expect.any(Object),
          }),
        );
      });

      it('passing ISubject should create a new form with FormGroup', () => {
        const formGroup = service.createSubjectFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
            major: expect.any(Object),
          }),
        );
      });
    });

    describe('getSubject', () => {
      it('should return NewSubject for default Subject initial value', () => {
        const formGroup = service.createSubjectFormGroup(sampleWithNewData);

        const subject = service.getSubject(formGroup) as any;

        expect(subject).toMatchObject(sampleWithNewData);
      });

      it('should return NewSubject for empty Subject initial value', () => {
        const formGroup = service.createSubjectFormGroup();

        const subject = service.getSubject(formGroup) as any;

        expect(subject).toMatchObject({});
      });

      it('should return ISubject', () => {
        const formGroup = service.createSubjectFormGroup(sampleWithRequiredData);

        const subject = service.getSubject(formGroup) as any;

        expect(subject).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISubject should not enable id FormControl', () => {
        const formGroup = service.createSubjectFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSubject should disable id FormControl', () => {
        const formGroup = service.createSubjectFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
