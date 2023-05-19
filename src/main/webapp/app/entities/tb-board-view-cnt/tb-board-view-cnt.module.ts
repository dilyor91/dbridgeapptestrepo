import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TbBoardViewCntComponent } from './list/tb-board-view-cnt.component';
import { TbBoardViewCntDetailComponent } from './detail/tb-board-view-cnt-detail.component';
import { TbBoardViewCntUpdateComponent } from './update/tb-board-view-cnt-update.component';
import { TbBoardViewCntDeleteDialogComponent } from './delete/tb-board-view-cnt-delete-dialog.component';
import { TbBoardViewCntRoutingModule } from './route/tb-board-view-cnt-routing.module';

@NgModule({
  imports: [SharedModule, TbBoardViewCntRoutingModule],
  declarations: [
    TbBoardViewCntComponent,
    TbBoardViewCntDetailComponent,
    TbBoardViewCntUpdateComponent,
    TbBoardViewCntDeleteDialogComponent,
  ],
})
export class TbBoardViewCntModule {}
