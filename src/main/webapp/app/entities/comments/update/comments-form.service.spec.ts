import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../comments.test-samples';

import { CommentsFormService } from './comments-form.service';

describe('Comments Form Service', () => {
  let service: CommentsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommentsFormService);
  });

  describe('Service methods', () => {
    describe('createCommentsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCommentsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            anonymousId: expect.any(Object),
            document: expect.any(Object),
          }),
        );
      });

      it('passing IComments should create a new form with FormGroup', () => {
        const formGroup = service.createCommentsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            anonymousId: expect.any(Object),
            document: expect.any(Object),
          }),
        );
      });
    });

    describe('getComments', () => {
      it('should return NewComments for default Comments initial value', () => {
        const formGroup = service.createCommentsFormGroup(sampleWithNewData);

        const comments = service.getComments(formGroup) as any;

        expect(comments).toMatchObject(sampleWithNewData);
      });

      it('should return NewComments for empty Comments initial value', () => {
        const formGroup = service.createCommentsFormGroup();

        const comments = service.getComments(formGroup) as any;

        expect(comments).toMatchObject({});
      });

      it('should return IComments', () => {
        const formGroup = service.createCommentsFormGroup(sampleWithRequiredData);

        const comments = service.getComments(formGroup) as any;

        expect(comments).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IComments should not enable id FormControl', () => {
        const formGroup = service.createCommentsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewComments should disable id FormControl', () => {
        const formGroup = service.createCommentsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
