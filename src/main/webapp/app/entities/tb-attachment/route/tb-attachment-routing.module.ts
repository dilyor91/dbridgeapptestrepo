import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TbAttachmentComponent } from '../list/tb-attachment.component';
import { TbAttachmentDetailComponent } from '../detail/tb-attachment-detail.component';
import { TbAttachmentUpdateComponent } from '../update/tb-attachment-update.component';
import { TbAttachmentRoutingResolveService } from './tb-attachment-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const tbAttachmentRoute: Routes = [
  {
    path: '',
    component: TbAttachmentComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TbAttachmentDetailComponent,
    resolve: {
      tbAttachment: TbAttachmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TbAttachmentUpdateComponent,
    resolve: {
      tbAttachment: TbAttachmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TbAttachmentUpdateComponent,
    resolve: {
      tbAttachment: TbAttachmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tbAttachmentRoute)],
  exports: [RouterModule],
})
export class TbAttachmentRoutingModule {}
