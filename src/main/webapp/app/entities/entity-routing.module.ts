import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'tb-board',
        data: { pageTitle: 'dbridgeapptestApp.tbBoard.home.title' },
        loadChildren: () => import('./tb-board/tb-board.module').then(m => m.TbBoardModule),
      },
      {
        path: 'tb-board-view-cnt',
        data: { pageTitle: 'dbridgeapptestApp.tbBoardViewCnt.home.title' },
        loadChildren: () => import('./tb-board-view-cnt/tb-board-view-cnt.module').then(m => m.TbBoardViewCntModule),
      },
      {
        path: 'tb-guide',
        data: { pageTitle: 'dbridgeapptestApp.tbGuide.home.title' },
        loadChildren: () => import('./tb-guide/tb-guide.module').then(m => m.TbGuideModule),
      },
      {
        path: 'tb-attachment',
        data: { pageTitle: 'dbridgeapptestApp.tbAttachment.home.title' },
        loadChildren: () => import('./tb-attachment/tb-attachment.module').then(m => m.TbAttachmentModule),
      },
      {
        path: 'tb-edu-mst',
        data: { pageTitle: 'dbridgeapptestApp.tbEduMst.home.title' },
        loadChildren: () => import('./tb-edu-mst/tb-edu-mst.module').then(m => m.TbEduMstModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
