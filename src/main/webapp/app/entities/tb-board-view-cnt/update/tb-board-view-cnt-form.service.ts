import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITbBoardViewCnt, NewTbBoardViewCnt } from '../tb-board-view-cnt.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITbBoardViewCnt for edit and NewTbBoardViewCntFormGroupInput for create.
 */
type TbBoardViewCntFormGroupInput = ITbBoardViewCnt | PartialWithRequiredKeyOf<NewTbBoardViewCnt>;

type TbBoardViewCntFormDefaults = Pick<NewTbBoardViewCnt, 'id'>;

type TbBoardViewCntFormGroupContent = {
  id: FormControl<ITbBoardViewCnt['id'] | NewTbBoardViewCnt['id']>;
  viewCnt: FormControl<ITbBoardViewCnt['viewCnt']>;
  bdSeq: FormControl<ITbBoardViewCnt['bdSeq']>;
};

export type TbBoardViewCntFormGroup = FormGroup<TbBoardViewCntFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TbBoardViewCntFormService {
  createTbBoardViewCntFormGroup(tbBoardViewCnt: TbBoardViewCntFormGroupInput = { id: null }): TbBoardViewCntFormGroup {
    const tbBoardViewCntRawValue = {
      ...this.getFormDefaults(),
      ...tbBoardViewCnt,
    };
    return new FormGroup<TbBoardViewCntFormGroupContent>({
      id: new FormControl(
        { value: tbBoardViewCntRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      viewCnt: new FormControl(tbBoardViewCntRawValue.viewCnt),
      bdSeq: new FormControl(tbBoardViewCntRawValue.bdSeq),
    });
  }

  getTbBoardViewCnt(form: TbBoardViewCntFormGroup): ITbBoardViewCnt | NewTbBoardViewCnt {
    return form.getRawValue() as ITbBoardViewCnt | NewTbBoardViewCnt;
  }

  resetForm(form: TbBoardViewCntFormGroup, tbBoardViewCnt: TbBoardViewCntFormGroupInput): void {
    const tbBoardViewCntRawValue = { ...this.getFormDefaults(), ...tbBoardViewCnt };
    form.reset(
      {
        ...tbBoardViewCntRawValue,
        id: { value: tbBoardViewCntRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TbBoardViewCntFormDefaults {
    return {
      id: null,
    };
  }
}
