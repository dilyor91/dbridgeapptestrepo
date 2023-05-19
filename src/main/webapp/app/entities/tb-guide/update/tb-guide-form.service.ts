import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITbGuide, NewTbGuide } from '../tb-guide.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITbGuide for edit and NewTbGuideFormGroupInput for create.
 */
type TbGuideFormGroupInput = ITbGuide | PartialWithRequiredKeyOf<NewTbGuide>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITbGuide | NewTbGuide> = Omit<T, 'regDate' | 'modDate'> & {
  regDate?: string | null;
  modDate?: string | null;
};

type TbGuideFormRawValue = FormValueOf<ITbGuide>;

type NewTbGuideFormRawValue = FormValueOf<NewTbGuide>;

type TbGuideFormDefaults = Pick<NewTbGuide, 'id' | 'regDate' | 'modDate'>;

type TbGuideFormGroupContent = {
  id: FormControl<TbGuideFormRawValue['id'] | NewTbGuide['id']>;
  title: FormControl<TbGuideFormRawValue['title']>;
  content: FormControl<TbGuideFormRawValue['content']>;
  status: FormControl<TbGuideFormRawValue['status']>;
  link: FormControl<TbGuideFormRawValue['link']>;
  regUser: FormControl<TbGuideFormRawValue['regUser']>;
  regDate: FormControl<TbGuideFormRawValue['regDate']>;
  modUser: FormControl<TbGuideFormRawValue['modUser']>;
  modDate: FormControl<TbGuideFormRawValue['modDate']>;
  eduSeq: FormControl<TbGuideFormRawValue['eduSeq']>;
};

export type TbGuideFormGroup = FormGroup<TbGuideFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TbGuideFormService {
  createTbGuideFormGroup(tbGuide: TbGuideFormGroupInput = { id: null }): TbGuideFormGroup {
    const tbGuideRawValue = this.convertTbGuideToTbGuideRawValue({
      ...this.getFormDefaults(),
      ...tbGuide,
    });
    return new FormGroup<TbGuideFormGroupContent>({
      id: new FormControl(
        { value: tbGuideRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(tbGuideRawValue.title),
      content: new FormControl(tbGuideRawValue.content),
      status: new FormControl(tbGuideRawValue.status),
      link: new FormControl(tbGuideRawValue.link),
      regUser: new FormControl(tbGuideRawValue.regUser),
      regDate: new FormControl(tbGuideRawValue.regDate),
      modUser: new FormControl(tbGuideRawValue.modUser),
      modDate: new FormControl(tbGuideRawValue.modDate),
      eduSeq: new FormControl(tbGuideRawValue.eduSeq),
    });
  }

  getTbGuide(form: TbGuideFormGroup): ITbGuide | NewTbGuide {
    return this.convertTbGuideRawValueToTbGuide(form.getRawValue() as TbGuideFormRawValue | NewTbGuideFormRawValue);
  }

  resetForm(form: TbGuideFormGroup, tbGuide: TbGuideFormGroupInput): void {
    const tbGuideRawValue = this.convertTbGuideToTbGuideRawValue({ ...this.getFormDefaults(), ...tbGuide });
    form.reset(
      {
        ...tbGuideRawValue,
        id: { value: tbGuideRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TbGuideFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      regDate: currentTime,
      modDate: currentTime,
    };
  }

  private convertTbGuideRawValueToTbGuide(rawTbGuide: TbGuideFormRawValue | NewTbGuideFormRawValue): ITbGuide | NewTbGuide {
    return {
      ...rawTbGuide,
      regDate: dayjs(rawTbGuide.regDate, DATE_TIME_FORMAT),
      modDate: dayjs(rawTbGuide.modDate, DATE_TIME_FORMAT),
    };
  }

  private convertTbGuideToTbGuideRawValue(
    tbGuide: ITbGuide | (Partial<NewTbGuide> & TbGuideFormDefaults)
  ): TbGuideFormRawValue | PartialWithRequiredKeyOf<NewTbGuideFormRawValue> {
    return {
      ...tbGuide,
      regDate: tbGuide.regDate ? tbGuide.regDate.format(DATE_TIME_FORMAT) : undefined,
      modDate: tbGuide.modDate ? tbGuide.modDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
