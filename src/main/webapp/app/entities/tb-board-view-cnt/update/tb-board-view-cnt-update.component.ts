import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TbBoardViewCntFormService, TbBoardViewCntFormGroup } from './tb-board-view-cnt-form.service';
import { ITbBoardViewCnt } from '../tb-board-view-cnt.model';
import { TbBoardViewCntService } from '../service/tb-board-view-cnt.service';
import { ITbBoard } from 'app/entities/tb-board/tb-board.model';
import { TbBoardService } from 'app/entities/tb-board/service/tb-board.service';

@Component({
  selector: 'jhi-tb-board-view-cnt-update',
  templateUrl: './tb-board-view-cnt-update.component.html',
})
export class TbBoardViewCntUpdateComponent implements OnInit {
  isSaving = false;
  tbBoardViewCnt: ITbBoardViewCnt | null = null;

  tbBoardsSharedCollection: ITbBoard[] = [];

  editForm: TbBoardViewCntFormGroup = this.tbBoardViewCntFormService.createTbBoardViewCntFormGroup();

  constructor(
    protected tbBoardViewCntService: TbBoardViewCntService,
    protected tbBoardViewCntFormService: TbBoardViewCntFormService,
    protected tbBoardService: TbBoardService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTbBoard = (o1: ITbBoard | null, o2: ITbBoard | null): boolean => this.tbBoardService.compareTbBoard(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tbBoardViewCnt }) => {
      this.tbBoardViewCnt = tbBoardViewCnt;
      if (tbBoardViewCnt) {
        this.updateForm(tbBoardViewCnt);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tbBoardViewCnt = this.tbBoardViewCntFormService.getTbBoardViewCnt(this.editForm);
    if (tbBoardViewCnt.id !== null) {
      this.subscribeToSaveResponse(this.tbBoardViewCntService.update(tbBoardViewCnt));
    } else {
      this.subscribeToSaveResponse(this.tbBoardViewCntService.create(tbBoardViewCnt));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITbBoardViewCnt>>): void {
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

  protected updateForm(tbBoardViewCnt: ITbBoardViewCnt): void {
    this.tbBoardViewCnt = tbBoardViewCnt;
    this.tbBoardViewCntFormService.resetForm(this.editForm, tbBoardViewCnt);

    this.tbBoardsSharedCollection = this.tbBoardService.addTbBoardToCollectionIfMissing<ITbBoard>(
      this.tbBoardsSharedCollection,
      tbBoardViewCnt.bdSeq
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tbBoardService
      .query()
      .pipe(map((res: HttpResponse<ITbBoard[]>) => res.body ?? []))
      .pipe(
        map((tbBoards: ITbBoard[]) => this.tbBoardService.addTbBoardToCollectionIfMissing<ITbBoard>(tbBoards, this.tbBoardViewCnt?.bdSeq))
      )
      .subscribe((tbBoards: ITbBoard[]) => (this.tbBoardsSharedCollection = tbBoards));
  }
}
