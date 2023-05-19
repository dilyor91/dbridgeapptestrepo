import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITbAttachment } from '../tb-attachment.model';
import { TbAttachmentService } from '../service/tb-attachment.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './tb-attachment-delete-dialog.component.html',
})
export class TbAttachmentDeleteDialogComponent {
  tbAttachment?: ITbAttachment;

  constructor(protected tbAttachmentService: TbAttachmentService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tbAttachmentService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
