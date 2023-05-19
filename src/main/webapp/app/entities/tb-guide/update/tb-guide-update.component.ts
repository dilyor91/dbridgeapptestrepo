import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TbGuideFormService, TbGuideFormGroup } from './tb-guide-form.service';
import { ITbGuide } from '../tb-guide.model';
import { TbGuideService } from '../service/tb-guide.service';
import { ITbEduMst } from 'app/entities/tb-edu-mst/tb-edu-mst.model';
import { TbEduMstService } from 'app/entities/tb-edu-mst/service/tb-edu-mst.service';

@Component({
  selector: 'jhi-tb-guide-update',
  templateUrl: './tb-guide-update.component.html',
})
export class TbGuideUpdateComponent implements OnInit {
  isSaving = false;
  tbGuide: ITbGuide | null = null;

  tbEduMstsSharedCollection: ITbEduMst[] = [];

  editForm: TbGuideFormGroup = this.tbGuideFormService.createTbGuideFormGroup();

  constructor(
    protected tbGuideService: TbGuideService,
    protected tbGuideFormService: TbGuideFormService,
    protected tbEduMstService: TbEduMstService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTbEduMst = (o1: ITbEduMst | null, o2: ITbEduMst | null): boolean => this.tbEduMstService.compareTbEduMst(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tbGuide }) => {
      this.tbGuide = tbGuide;
      if (tbGuide) {
        this.updateForm(tbGuide);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tbGuide = this.tbGuideFormService.getTbGuide(this.editForm);
    if (tbGuide.id !== null) {
      this.subscribeToSaveResponse(this.tbGuideService.update(tbGuide));
    } else {
      this.subscribeToSaveResponse(this.tbGuideService.create(tbGuide));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITbGuide>>): void {
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

  protected updateForm(tbGuide: ITbGuide): void {
    this.tbGuide = tbGuide;
    this.tbGuideFormService.resetForm(this.editForm, tbGuide);

    this.tbEduMstsSharedCollection = this.tbEduMstService.addTbEduMstToCollectionIfMissing<ITbEduMst>(
      this.tbEduMstsSharedCollection,
      tbGuide.eduSeq
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tbEduMstService
      .query()
      .pipe(map((res: HttpResponse<ITbEduMst[]>) => res.body ?? []))
      .pipe(
        map((tbEduMsts: ITbEduMst[]) => this.tbEduMstService.addTbEduMstToCollectionIfMissing<ITbEduMst>(tbEduMsts, this.tbGuide?.eduSeq))
      )
      .subscribe((tbEduMsts: ITbEduMst[]) => (this.tbEduMstsSharedCollection = tbEduMsts));
  }
}
