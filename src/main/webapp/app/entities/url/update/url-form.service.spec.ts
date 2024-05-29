import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../url.test-samples';

import { UrlFormService } from './url-form.service';

describe('Url Form Service', () => {
  let service: UrlFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UrlFormService);
  });

  describe('Service methods', () => {
    describe('createUrlFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUrlFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            driveUrl: expect.any(Object),
            type: expect.any(Object),
            document: expect.any(Object),
          }),
        );
      });

      it('passing IUrl should create a new form with FormGroup', () => {
        const formGroup = service.createUrlFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            driveUrl: expect.any(Object),
            type: expect.any(Object),
            document: expect.any(Object),
          }),
        );
      });
    });

    describe('getUrl', () => {
      it('should return NewUrl for default Url initial value', () => {
        const formGroup = service.createUrlFormGroup(sampleWithNewData);

        const url = service.getUrl(formGroup) as any;

        expect(url).toMatchObject(sampleWithNewData);
      });

      it('should return NewUrl for empty Url initial value', () => {
        const formGroup = service.createUrlFormGroup();

        const url = service.getUrl(formGroup) as any;

        expect(url).toMatchObject({});
      });

      it('should return IUrl', () => {
        const formGroup = service.createUrlFormGroup(sampleWithRequiredData);

        const url = service.getUrl(formGroup) as any;

        expect(url).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUrl should not enable id FormControl', () => {
        const formGroup = service.createUrlFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUrl should disable id FormControl', () => {
        const formGroup = service.createUrlFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
