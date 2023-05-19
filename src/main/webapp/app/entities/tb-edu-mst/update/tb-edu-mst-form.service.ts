import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITbEduMst, NewTbEduMst } from '../tb-edu-mst.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITbEduMst for edit and NewTbEduMstFormGroupInput for create.
 */
type TbEduMstFormGroupInput = ITbEduMst | PartialWithRequiredKeyOf<NewTbEduMst>;

type TbEduMstFormDefaults = Pick<NewTbEduMst, 'id'>;

type TbEduMstFormGroupContent = {
  id: FormControl<ITbEduMst['id'] | NewTbEduMst['id']>;
  viewCnt: FormControl<ITbEduMst['viewCnt']>;
};

export type TbEduMstFormGroup = FormGroup<TbEduMstFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TbEduMstFormService {
  createTbEduMstFormGroup(tbEduMst: TbEduMstFormGroupInput = { id: null }): TbEduMstFormGroup {
    const tbEduMstRawValue = {
      ...this.getFormDefaults(),
      ...tbEduMst,
    };
    return new FormGroup<TbEduMstFormGroupContent>({
      id: new FormControl(
        { value: tbEduMstRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      viewCnt: new FormControl(tbEduMstRawValue.viewCnt),
    });
  }

  getTbEduMst(form: TbEduMstFormGroup): ITbEduMst | NewTbEduMst {
    return form.getRawValue() as ITbEduMst | NewTbEduMst;
  }

  resetForm(form: TbEduMstFormGroup, tbEduMst: TbEduMstFormGroupInput): void {
    const tbEduMstRawValue = { ...this.getFormDefaults(), ...tbEduMst };
    form.reset(
      {
        ...tbEduMstRawValue,
        id: { value: tbEduMstRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TbEduMstFormDefaults {
    return {
      id: null,
    };
  }
}
