import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITbGuide } from '../tb-guide.model';
import { TbGuideService } from '../service/tb-guide.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './tb-guide-delete-dialog.component.html',
})
export class TbGuideDeleteDialogComponent {
  tbGuide?: ITbGuide;

  constructor(protected tbGuideService: TbGuideService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tbGuideService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
