import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITbEduMst } from '../tb-edu-mst.model';
import { TbEduMstService } from '../service/tb-edu-mst.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './tb-edu-mst-delete-dialog.component.html',
})
export class TbEduMstDeleteDialogComponent {
  tbEduMst?: ITbEduMst;

  constructor(protected tbEduMstService: TbEduMstService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tbEduMstService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
