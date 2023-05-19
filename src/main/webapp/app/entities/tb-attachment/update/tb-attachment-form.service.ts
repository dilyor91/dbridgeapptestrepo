import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITbAttachment, NewTbAttachment } from '../tb-attachment.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITbAttachment for edit and NewTbAttachmentFormGroupInput for create.
 */
type TbAttachmentFormGroupInput = ITbAttachment | PartialWithRequiredKeyOf<NewTbAttachment>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITbAttachment | NewTbAttachment> = Omit<T, 'regDate' | 'modDate'> & {
  regDate?: string | null;
  modDate?: string | null;
};

type TbAttachmentFormRawValue = FormValueOf<ITbAttachment>;

type NewTbAttachmentFormRawValue = FormValueOf<NewTbAttachment>;

type TbAttachmentFormDefaults = Pick<NewTbAttachment, 'id' | 'regDate' | 'modDate'>;

type TbAttachmentFormGroupContent = {
  id: FormControl<TbAttachmentFormRawValue['id'] | NewTbAttachment['id']>;
  attType: FormControl<TbAttachmentFormRawValue['attType']>;
  name: FormControl<TbAttachmentFormRawValue['name']>;
  path: FormControl<TbAttachmentFormRawValue['path']>;
  fileSize: FormControl<TbAttachmentFormRawValue['fileSize']>;
  ext: FormControl<TbAttachmentFormRawValue['ext']>;
  regUser: FormControl<TbAttachmentFormRawValue['regUser']>;
  regDate: FormControl<TbAttachmentFormRawValue['regDate']>;
  modUser: FormControl<TbAttachmentFormRawValue['modUser']>;
  modDate: FormControl<TbAttachmentFormRawValue['modDate']>;
  bdSeq: FormControl<TbAttachmentFormRawValue['bdSeq']>;
  gdSeq: FormControl<TbAttachmentFormRawValue['gdSeq']>;
};

export type TbAttachmentFormGroup = FormGroup<TbAttachmentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TbAttachmentFormService {
  createTbAttachmentFormGroup(tbAttachment: TbAttachmentFormGroupInput = { id: null }): TbAttachmentFormGroup {
    const tbAttachmentRawValue = this.convertTbAttachmentToTbAttachmentRawValue({
      ...this.getFormDefaults(),
      ...tbAttachment,
    });
    return new FormGroup<TbAttachmentFormGroupContent>({
      id: new FormControl(
        { value: tbAttachmentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      attType: new FormControl(tbAttachmentRawValue.attType),
      name: new FormControl(tbAttachmentRawValue.name),
      path: new FormControl(tbAttachmentRawValue.path),
      fileSize: new FormControl(tbAttachmentRawValue.fileSize),
      ext: new FormControl(tbAttachmentRawValue.ext),
      regUser: new FormControl(tbAttachmentRawValue.regUser),
      regDate: new FormControl(tbAttachmentRawValue.regDate),
      modUser: new FormControl(tbAttachmentRawValue.modUser),
      modDate: new FormControl(tbAttachmentRawValue.modDate),
      bdSeq: new FormControl(tbAttachmentRawValue.bdSeq),
      gdSeq: new FormControl(tbAttachmentRawValue.gdSeq),
    });
  }

  getTbAttachment(form: TbAttachmentFormGroup): ITbAttachment | NewTbAttachment {
    return this.convertTbAttachmentRawValueToTbAttachment(form.getRawValue() as TbAttachmentFormRawValue | NewTbAttachmentFormRawValue);
  }

  resetForm(form: TbAttachmentFormGroup, tbAttachment: TbAttachmentFormGroupInput): void {
    const tbAttachmentRawValue = this.convertTbAttachmentToTbAttachmentRawValue({ ...this.getFormDefaults(), ...tbAttachment });
    form.reset(
      {
        ...tbAttachmentRawValue,
        id: { value: tbAttachmentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TbAttachmentFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      regDate: currentTime,
      modDate: currentTime,
    };
  }

  private convertTbAttachmentRawValueToTbAttachment(
    rawTbAttachment: TbAttachmentFormRawValue | NewTbAttachmentFormRawValue
  ): ITbAttachment | NewTbAttachment {
    return {
      ...rawTbAttachment,
      regDate: dayjs(rawTbAttachment.regDate, DATE_TIME_FORMAT),
      modDate: dayjs(rawTbAttachment.modDate, DATE_TIME_FORMAT),
    };
  }

  private convertTbAttachmentToTbAttachmentRawValue(
    tbAttachment: ITbAttachment | (Partial<NewTbAttachment> & TbAttachmentFormDefaults)
  ): TbAttachmentFormRawValue | PartialWithRequiredKeyOf<NewTbAttachmentFormRawValue> {
    return {
      ...tbAttachment,
      regDate: tbAttachment.regDate ? tbAttachment.regDate.format(DATE_TIME_FORMAT) : undefined,
      modDate: tbAttachment.modDate ? tbAttachment.modDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
