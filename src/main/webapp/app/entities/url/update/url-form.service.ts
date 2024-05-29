import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUrl, NewUrl } from '../url.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUrl for edit and NewUrlFormGroupInput for create.
 */
type UrlFormGroupInput = IUrl | PartialWithRequiredKeyOf<NewUrl>;

type UrlFormDefaults = Pick<NewUrl, 'id'>;

type UrlFormGroupContent = {
  id: FormControl<IUrl['id'] | NewUrl['id']>;
  driveUrl: FormControl<IUrl['driveUrl']>;
  type: FormControl<IUrl['type']>;
  document: FormControl<IUrl['document']>;
};

export type UrlFormGroup = FormGroup<UrlFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UrlFormService {
  createUrlFormGroup(url: UrlFormGroupInput = { id: null }): UrlFormGroup {
    const urlRawValue = {
      ...this.getFormDefaults(),
      ...url,
    };
    return new FormGroup<UrlFormGroupContent>({
      id: new FormControl(
        { value: urlRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      driveUrl: new FormControl(urlRawValue.driveUrl),
      type: new FormControl(urlRawValue.type),
      document: new FormControl(urlRawValue.document),
    });
  }

  getUrl(form: UrlFormGroup): IUrl | NewUrl {
    return form.getRawValue() as IUrl | NewUrl;
  }

  resetForm(form: UrlFormGroup, url: UrlFormGroupInput): void {
    const urlRawValue = { ...this.getFormDefaults(), ...url };
    form.reset(
      {
        ...urlRawValue,
        id: { value: urlRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): UrlFormDefaults {
    return {
      id: null,
    };
  }
}
