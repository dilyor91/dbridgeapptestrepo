import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITbBoardViewCnt } from '../tb-board-view-cnt.model';

@Component({
  selector: 'jhi-tb-board-view-cnt-detail',
  templateUrl: './tb-board-view-cnt-detail.component.html',
})
export class TbBoardViewCntDetailComponent implements OnInit {
  tbBoardViewCnt: ITbBoardViewCnt | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tbBoardViewCnt }) => {
      this.tbBoardViewCnt = tbBoardViewCnt;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
