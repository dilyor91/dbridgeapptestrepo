import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TbAttachmentComponent } from './list/tb-attachment.component';
import { TbAttachmentDetailComponent } from './detail/tb-attachment-detail.component';
import { TbAttachmentUpdateComponent } from './update/tb-attachment-update.component';
import { TbAttachmentDeleteDialogComponent } from './delete/tb-attachment-delete-dialog.component';
import { TbAttachmentRoutingModule } from './route/tb-attachment-routing.module';

@NgModule({
  imports: [SharedModule, TbAttachmentRoutingModule],
  declarations: [TbAttachmentComponent, TbAttachmentDetailComponent, TbAttachmentUpdateComponent, TbAttachmentDeleteDialogComponent],
})
export class TbAttachmentModule {}
