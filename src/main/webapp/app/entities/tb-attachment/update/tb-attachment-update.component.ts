import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TbAttachmentFormService, TbAttachmentFormGroup } from './tb-attachment-form.service';
import { ITbAttachment } from '../tb-attachment.model';
import { TbAttachmentService } from '../service/tb-attachment.service';
import { ITbBoard } from 'app/entities/tb-board/tb-board.model';
import { TbBoardService } from 'app/entities/tb-board/service/tb-board.service';
import { ITbGuide } from 'app/entities/tb-guide/tb-guide.model';
import { TbGuideService } from 'app/entities/tb-guide/service/tb-guide.service';

@Component({
  selector: 'jhi-tb-attachment-update',
  templateUrl: './tb-attachment-update.component.html',
})
export class TbAttachmentUpdateComponent implements OnInit {
  isSaving = false;
  tbAttachment: ITbAttachment | null = null;

  tbBoardsSharedCollection: ITbBoard[] = [];
  tbGuidesSharedCollection: ITbGuide[] = [];

  editForm: TbAttachmentFormGroup = this.tbAttachmentFormService.createTbAttachmentFormGroup();

  constructor(
    protected tbAttachmentService: TbAttachmentService,
    protected tbAttachmentFormService: TbAttachmentFormService,
    protected tbBoardService: TbBoardService,
    protected tbGuideService: TbGuideService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTbBoard = (o1: ITbBoard | null, o2: ITbBoard | null): boolean => this.tbBoardService.compareTbBoard(o1, o2);

  compareTbGuide = (o1: ITbGuide | null, o2: ITbGuide | null): boolean => this.tbGuideService.compareTbGuide(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tbAttachment }) => {
      this.tbAttachment = tbAttachment;
      if (tbAttachment) {
        this.updateForm(tbAttachment);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tbAttachment = this.tbAttachmentFormService.getTbAttachment(this.editForm);
    if (tbAttachment.id !== null) {
      this.subscribeToSaveResponse(this.tbAttachmentService.update(tbAttachment));
    } else {
      this.subscribeToSaveResponse(this.tbAttachmentService.create(tbAttachment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITbAttachment>>): void {
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

  protected updateForm(tbAttachment: ITbAttachment): void {
    this.tbAttachment = tbAttachment;
    this.tbAttachmentFormService.resetForm(this.editForm, tbAttachment);

    this.tbBoardsSharedCollection = this.tbBoardService.addTbBoardToCollectionIfMissing<ITbBoard>(
      this.tbBoardsSharedCollection,
      tbAttachment.bdSeq
    );
    this.tbGuidesSharedCollection = this.tbGuideService.addTbGuideToCollectionIfMissing<ITbGuide>(
      this.tbGuidesSharedCollection,
      tbAttachment.gdSeq
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tbBoardService
      .query()
      .pipe(map((res: HttpResponse<ITbBoard[]>) => res.body ?? []))
      .pipe(
        map((tbBoards: ITbBoard[]) => this.tbBoardService.addTbBoardToCollectionIfMissing<ITbBoard>(tbBoards, this.tbAttachment?.bdSeq))
      )
      .subscribe((tbBoards: ITbBoard[]) => (this.tbBoardsSharedCollection = tbBoards));

    this.tbGuideService
      .query()
      .pipe(map((res: HttpResponse<ITbGuide[]>) => res.body ?? []))
      .pipe(
        map((tbGuides: ITbGuide[]) => this.tbGuideService.addTbGuideToCollectionIfMissing<ITbGuide>(tbGuides, this.tbAttachment?.gdSeq))
      )
      .subscribe((tbGuides: ITbGuide[]) => (this.tbGuidesSharedCollection = tbGuides));
  }
}
