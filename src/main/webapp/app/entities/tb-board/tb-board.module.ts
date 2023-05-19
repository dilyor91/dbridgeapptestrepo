import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TbBoardComponent } from './list/tb-board.component';
import { TbBoardDetailComponent } from './detail/tb-board-detail.component';
import { TbBoardUpdateComponent } from './update/tb-board-update.component';
import { TbBoardDeleteDialogComponent } from './delete/tb-board-delete-dialog.component';
import { TbBoardRoutingModule } from './route/tb-board-routing.module';

@NgModule({
  imports: [SharedModule, TbBoardRoutingModule],
  declarations: [TbBoardComponent, TbBoardDetailComponent, TbBoardUpdateComponent, TbBoardDeleteDialogComponent],
})
export class TbBoardModule {}
