import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITbAttachment } from '../tb-attachment.model';

@Component({
  selector: 'jhi-tb-attachment-detail',
  templateUrl: './tb-attachment-detail.component.html',
})
export class TbAttachmentDetailComponent implements OnInit {
  tbAttachment: ITbAttachment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tbAttachment }) => {
      this.tbAttachment = tbAttachment;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
