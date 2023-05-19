import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITbGuide } from '../tb-guide.model';

@Component({
  selector: 'jhi-tb-guide-detail',
  templateUrl: './tb-guide-detail.component.html',
})
export class TbGuideDetailComponent implements OnInit {
  tbGuide: ITbGuide | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tbGuide }) => {
      this.tbGuide = tbGuide;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
