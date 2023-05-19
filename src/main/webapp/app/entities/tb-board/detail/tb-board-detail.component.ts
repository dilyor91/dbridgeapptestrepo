import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITbBoard } from '../tb-board.model';

@Component({
  selector: 'jhi-tb-board-detail',
  templateUrl: './tb-board-detail.component.html',
})
export class TbBoardDetailComponent implements OnInit {
  tbBoard: ITbBoard | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tbBoard }) => {
      this.tbBoard = tbBoard;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
