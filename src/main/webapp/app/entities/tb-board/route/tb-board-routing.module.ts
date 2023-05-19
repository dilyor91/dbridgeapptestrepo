import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TbBoardComponent } from '../list/tb-board.component';
import { TbBoardDetailComponent } from '../detail/tb-board-detail.component';
import { TbBoardUpdateComponent } from '../update/tb-board-update.component';
import { TbBoardRoutingResolveService } from './tb-board-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const tbBoardRoute: Routes = [
  {
    path: '',
    component: TbBoardComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TbBoardDetailComponent,
    resolve: {
      tbBoard: TbBoardRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TbBoardUpdateComponent,
    resolve: {
      tbBoard: TbBoardRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TbBoardUpdateComponent,
    resolve: {
      tbBoard: TbBoardRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tbBoardRoute)],
  exports: [RouterModule],
})
export class TbBoardRoutingModule {}
