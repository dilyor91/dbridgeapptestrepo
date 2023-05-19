import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TbBoardFormService, TbBoardFormGroup } from './tb-board-form.service';
import { ITbBoard } from '../tb-board.model';
import { TbBoardService } from '../service/tb-board.service';

@Component({
  selector: 'jhi-tb-board-update',
  templateUrl: './tb-board-update.component.html',
})
export class TbBoardUpdateComponent implements OnInit {
  isSaving = false;
  tbBoard: ITbBoard | null = null;

  editForm: TbBoardFormGroup = this.tbBoardFormService.createTbBoardFormGroup();

  constructor(
    protected tbBoardService: TbBoardService,
    protected tbBoardFormService: TbBoardFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tbBoard }) => {
      this.tbBoard = tbBoard;
      if (tbBoard) {
        this.updateForm(tbBoard);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tbBoard = this.tbBoardFormService.getTbBoard(this.editForm);
    if (tbBoard.id !== null) {
      this.subscribeToSaveResponse(this.tbBoardService.update(tbBoard));
    } else {
      this.subscribeToSaveResponse(this.tbBoardService.create(tbBoard));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITbBoard>>): void {
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

  protected updateForm(tbBoard: ITbBoard): void {
    this.tbBoard = tbBoard;
    this.tbBoardFormService.resetForm(this.editForm, tbBoard);
  }
}
