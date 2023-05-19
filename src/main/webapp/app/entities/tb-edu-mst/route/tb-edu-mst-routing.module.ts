import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TbEduMstComponent } from '../list/tb-edu-mst.component';
import { TbEduMstDetailComponent } from '../detail/tb-edu-mst-detail.component';
import { TbEduMstUpdateComponent } from '../update/tb-edu-mst-update.component';
import { TbEduMstRoutingResolveService } from './tb-edu-mst-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const tbEduMstRoute: Routes = [
  {
    path: '',
    component: TbEduMstComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TbEduMstDetailComponent,
    resolve: {
      tbEduMst: TbEduMstRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TbEduMstUpdateComponent,
    resolve: {
      tbEduMst: TbEduMstRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TbEduMstUpdateComponent,
    resolve: {
      tbEduMst: TbEduMstRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tbEduMstRoute)],
  exports: [RouterModule],
})
export class TbEduMstRoutingModule {}
