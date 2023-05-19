import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TbGuideComponent } from './list/tb-guide.component';
import { TbGuideDetailComponent } from './detail/tb-guide-detail.component';
import { TbGuideUpdateComponent } from './update/tb-guide-update.component';
import { TbGuideDeleteDialogComponent } from './delete/tb-guide-delete-dialog.component';
import { TbGuideRoutingModule } from './route/tb-guide-routing.module';

@NgModule({
  imports: [SharedModule, TbGuideRoutingModule],
  declarations: [TbGuideComponent, TbGuideDetailComponent, TbGuideUpdateComponent, TbGuideDeleteDialogComponent],
})
export class TbGuideModule {}
