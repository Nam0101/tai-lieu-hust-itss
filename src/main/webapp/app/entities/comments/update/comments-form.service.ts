import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IComments, NewComments } from '../comments.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IComments for edit and NewCommentsFormGroupInput for create.
 */
type CommentsFormGroupInput = IComments | PartialWithRequiredKeyOf<NewComments>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IComments | NewComments> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

type CommentsFormRawValue = FormValueOf<IComments>;

type NewCommentsFormRawValue = FormValueOf<NewComments>;

type CommentsFormDefaults = Pick<NewComments, 'id' | 'createdAt' | 'updatedAt'>;

type CommentsFormGroupContent = {
  id: FormControl<CommentsFormRawValue['id'] | NewComments['id']>;
  createdAt: FormControl<CommentsFormRawValue['createdAt']>;
  updatedAt: FormControl<CommentsFormRawValue['updatedAt']>;
  anonymousId: FormControl<CommentsFormRawValue['anonymousId']>;
  document: FormControl<CommentsFormRawValue['document']>;
};

export type CommentsFormGroup = FormGroup<CommentsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CommentsFormService {
  createCommentsFormGroup(comments: CommentsFormGroupInput = { id: null }): CommentsFormGroup {
    const commentsRawValue = this.convertCommentsToCommentsRawValue({
      ...this.getFormDefaults(),
      ...comments,
    });
    return new FormGroup<CommentsFormGroupContent>({
      id: new FormControl(
        { value: commentsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      createdAt: new FormControl(commentsRawValue.createdAt),
      updatedAt: new FormControl(commentsRawValue.updatedAt),
      anonymousId: new FormControl(commentsRawValue.anonymousId),
      document: new FormControl(commentsRawValue.document),
    });
  }

  getComments(form: CommentsFormGroup): IComments | NewComments {
    return this.convertCommentsRawValueToComments(form.getRawValue() as CommentsFormRawValue | NewCommentsFormRawValue);
  }

  resetForm(form: CommentsFormGroup, comments: CommentsFormGroupInput): void {
    const commentsRawValue = this.convertCommentsToCommentsRawValue({ ...this.getFormDefaults(), ...comments });
    form.reset(
      {
        ...commentsRawValue,
        id: { value: commentsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CommentsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      updatedAt: currentTime,
    };
  }

  private convertCommentsRawValueToComments(rawComments: CommentsFormRawValue | NewCommentsFormRawValue): IComments | NewComments {
    return {
      ...rawComments,
      createdAt: dayjs(rawComments.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawComments.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertCommentsToCommentsRawValue(
    comments: IComments | (Partial<NewComments> & CommentsFormDefaults),
  ): CommentsFormRawValue | PartialWithRequiredKeyOf<NewCommentsFormRawValue> {
    return {
      ...comments,
      createdAt: comments.createdAt ? comments.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: comments.updatedAt ? comments.updatedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
