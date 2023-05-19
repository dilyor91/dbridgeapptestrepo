import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TbGuideComponent } from '../list/tb-guide.component';
import { TbGuideDetailComponent } from '../detail/tb-guide-detail.component';
import { TbGuideUpdateComponent } from '../update/tb-guide-update.component';
import { TbGuideRoutingResolveService } from './tb-guide-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const tbGuideRoute: Routes = [
  {
    path: '',
    component: TbGuideComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TbGuideDetailComponent,
    resolve: {
      tbGuide: TbGuideRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TbGuideUpdateComponent,
    resolve: {
      tbGuide: TbGuideRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TbGuideUpdateComponent,
    resolve: {
      tbGuide: TbGuideRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tbGuideRoute)],
  exports: [RouterModule],
})
export class TbGuideRoutingModule {}
