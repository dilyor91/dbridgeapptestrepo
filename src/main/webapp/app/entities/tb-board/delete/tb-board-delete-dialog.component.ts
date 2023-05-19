import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITbBoard } from '../tb-board.model';
import { TbBoardService } from '../service/tb-board.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './tb-board-delete-dialog.component.html',
})
export class TbBoardDeleteDialogComponent {
  tbBoard?: ITbBoard;

  constructor(protected tbBoardService: TbBoardService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tbBoardService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
