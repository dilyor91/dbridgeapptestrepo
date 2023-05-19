import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITbBoardViewCnt } from '../tb-board-view-cnt.model';
import { TbBoardViewCntService } from '../service/tb-board-view-cnt.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './tb-board-view-cnt-delete-dialog.component.html',
})
export class TbBoardViewCntDeleteDialogComponent {
  tbBoardViewCnt?: ITbBoardViewCnt;

  constructor(protected tbBoardViewCntService: TbBoardViewCntService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tbBoardViewCntService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
