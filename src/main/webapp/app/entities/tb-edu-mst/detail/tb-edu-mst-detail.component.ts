import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITbEduMst } from '../tb-edu-mst.model';

@Component({
  selector: 'jhi-tb-edu-mst-detail',
  templateUrl: './tb-edu-mst-detail.component.html',
})
export class TbEduMstDetailComponent implements OnInit {
  tbEduMst: ITbEduMst | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tbEduMst }) => {
      this.tbEduMst = tbEduMst;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
