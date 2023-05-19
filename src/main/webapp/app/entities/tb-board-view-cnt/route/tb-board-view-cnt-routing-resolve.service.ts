import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITbBoardViewCnt } from '../tb-board-view-cnt.model';
import { TbBoardViewCntService } from '../service/tb-board-view-cnt.service';

@Injectable({ providedIn: 'root' })
export class TbBoardViewCntRoutingResolveService implements Resolve<ITbBoardViewCnt | null> {
  constructor(protected service: TbBoardViewCntService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITbBoardViewCnt | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tbBoardViewCnt: HttpResponse<ITbBoardViewCnt>) => {
          if (tbBoardViewCnt.body) {
            return of(tbBoardViewCnt.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
