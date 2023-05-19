import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TbEduMstFormService, TbEduMstFormGroup } from './tb-edu-mst-form.service';
import { ITbEduMst } from '../tb-edu-mst.model';
import { TbEduMstService } from '../service/tb-edu-mst.service';

@Component({
  selector: 'jhi-tb-edu-mst-update',
  templateUrl: './tb-edu-mst-update.component.html',
})
export class TbEduMstUpdateComponent implements OnInit {
  isSaving = false;
  tbEduMst: ITbEduMst | null = null;

  editForm: TbEduMstFormGroup = this.tbEduMstFormService.createTbEduMstFormGroup();

  constructor(
    protected tbEduMstService: TbEduMstService,
    protected tbEduMstFormService: TbEduMstFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tbEduMst }) => {
      this.tbEduMst = tbEduMst;
      if (tbEduMst) {
        this.updateForm(tbEduMst);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tbEduMst = this.tbEduMstFormService.getTbEduMst(this.editForm);
    if (tbEduMst.id !== null) {
      this.subscribeToSaveResponse(this.tbEduMstService.update(tbEduMst));
    } else {
      this.subscribeToSaveResponse(this.tbEduMstService.create(tbEduMst));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITbEduMst>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(tbEduMst: ITbEduMst): void {
    this.tbEduMst = tbEduMst;
    this.tbEduMstFormService.resetForm(this.editForm, tbEduMst);
  }
}
