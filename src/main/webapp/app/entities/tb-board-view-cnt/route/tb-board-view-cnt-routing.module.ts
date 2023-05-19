import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TbBoardViewCntComponent } from '../list/tb-board-view-cnt.component';
import { TbBoardViewCntDetailComponent } from '../detail/tb-board-view-cnt-detail.component';
import { TbBoardViewCntUpdateComponent } from '../update/tb-board-view-cnt-update.component';
import { TbBoardViewCntRoutingResolveService } from './tb-board-view-cnt-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const tbBoardViewCntRoute: Routes = [
  {
    path: '',
    component: TbBoardViewCntComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TbBoardViewCntDetailComponent,
    resolve: {
      tbBoardViewCnt: TbBoardViewCntRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TbBoardViewCntUpdateComponent,
    resolve: {
      tbBoardViewCnt: TbBoardViewCntRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TbBoardViewCntUpdateComponent,
    resolve: {
      tbBoardViewCnt: TbBoardViewCntRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tbBoardViewCntRoute)],
  exports: [RouterModule],
})
export class TbBoardViewCntRoutingModule {}
