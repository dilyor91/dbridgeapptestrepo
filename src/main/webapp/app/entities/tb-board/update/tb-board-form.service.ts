import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITbBoard, NewTbBoard } from '../tb-board.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITbBoard for edit and NewTbBoardFormGroupInput for create.
 */
type TbBoardFormGroupInput = ITbBoard | PartialWithRequiredKeyOf<NewTbBoard>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITbBoard | NewTbBoard> = Omit<T, 'publishedDate' | 'regDate' | 'modDate'> & {
  publishedDate?: string | null;
  regDate?: string | null;
  modDate?: string | null;
};

type TbBoardFormRawValue = FormValueOf<ITbBoard>;

type NewTbBoardFormRawValue = FormValueOf<NewTbBoard>;

type TbBoardFormDefaults = Pick<NewTbBoard, 'id' | 'publishedDate' | 'regDate' | 'modDate'>;

type TbBoardFormGroupContent = {
  id: FormControl<TbBoardFormRawValue['id'] | NewTbBoard['id']>;
  bdType: FormControl<TbBoardFormRawValue['bdType']>;
  title: FormControl<TbBoardFormRawValue['title']>;
  content: FormControl<TbBoardFormRawValue['content']>;
  publishedDate: FormControl<TbBoardFormRawValue['publishedDate']>;
  status: FormControl<TbBoardFormRawValue['status']>;
  boardOrder: FormControl<TbBoardFormRawValue['boardOrder']>;
  regUser: FormControl<TbBoardFormRawValue['regUser']>;
  regDate: FormControl<TbBoardFormRawValue['regDate']>;
  modUser: FormControl<TbBoardFormRawValue['modUser']>;
  modDate: FormControl<TbBoardFormRawValue['modDate']>;
};

export type TbBoardFormGroup = FormGroup<TbBoardFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TbBoardFormService {
  createTbBoardFormGroup(tbBoard: TbBoardFormGroupInput = { id: null }): TbBoardFormGroup {
    const tbBoardRawValue = this.convertTbBoardToTbBoardRawValue({
      ...this.getFormDefaults(),
      ...tbBoard,
    });
    return new FormGroup<TbBoardFormGroupContent>({
      id: new FormControl(
        { value: tbBoardRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      bdType: new FormControl(tbBoardRawValue.bdType),
      title: new FormControl(tbBoardRawValue.title),
      content: new FormControl(tbBoardRawValue.content),
      publishedDate: new FormControl(tbBoardRawValue.publishedDate),
      status: new FormControl(tbBoardRawValue.status),
      boardOrder: new FormControl(tbBoardRawValue.boardOrder),
      regUser: new FormControl(tbBoardRawValue.regUser),
      regDate: new FormControl(tbBoardRawValue.regDate),
      modUser: new FormControl(tbBoardRawValue.modUser),
      modDate: new FormControl(tbBoardRawValue.modDate),
    });
  }

  getTbBoard(form: TbBoardFormGroup): ITbBoard | NewTbBoard {
    return this.convertTbBoardRawValueToTbBoard(form.getRawValue() as TbBoardFormRawValue | NewTbBoardFormRawValue);
  }

  resetForm(form: TbBoardFormGroup, tbBoard: TbBoardFormGroupInput): void {
    const tbBoardRawValue = this.convertTbBoardToTbBoardRawValue({ ...this.getFormDefaults(), ...tbBoard });
    form.reset(
      {
        ...tbBoardRawValue,
        id: { value: tbBoardRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TbBoardFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      publishedDate: currentTime,
      regDate: currentTime,
      modDate: currentTime,
    };
  }

  private convertTbBoardRawValueToTbBoard(rawTbBoard: TbBoardFormRawValue | NewTbBoardFormRawValue): ITbBoard | NewTbBoard {
    return {
      ...rawTbBoard,
      publishedDate: dayjs(rawTbBoard.publishedDate, DATE_TIME_FORMAT),
      regDate: dayjs(rawTbBoard.regDate, DATE_TIME_FORMAT),
      modDate: dayjs(rawTbBoard.modDate, DATE_TIME_FORMAT),
    };
  }

  private convertTbBoardToTbBoardRawValue(
    tbBoard: ITbBoard | (Partial<NewTbBoard> & TbBoardFormDefaults)
  ): TbBoardFormRawValue | PartialWithRequiredKeyOf<NewTbBoardFormRawValue> {
    return {
      ...tbBoard,
      publishedDate: tbBoard.publishedDate ? tbBoard.publishedDate.format(DATE_TIME_FORMAT) : undefined,
      regDate: tbBoard.regDate ? tbBoard.regDate.format(DATE_TIME_FORMAT) : undefined,
      modDate: tbBoard.modDate ? tbBoard.modDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
