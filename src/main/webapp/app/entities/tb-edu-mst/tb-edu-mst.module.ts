import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TbEduMstComponent } from './list/tb-edu-mst.component';
import { TbEduMstDetailComponent } from './detail/tb-edu-mst-detail.component';
import { TbEduMstUpdateComponent } from './update/tb-edu-mst-update.component';
import { TbEduMstDeleteDialogComponent } from './delete/tb-edu-mst-delete-dialog.component';
import { TbEduMstRoutingModule } from './route/tb-edu-mst-routing.module';

@NgModule({
  imports: [SharedModule, TbEduMstRoutingModule],
  declarations: [TbEduMstComponent, TbEduMstDetailComponent, TbEduMstUpdateComponent, TbEduMstDeleteDialogComponent],
})
export class TbEduMstModule {}
