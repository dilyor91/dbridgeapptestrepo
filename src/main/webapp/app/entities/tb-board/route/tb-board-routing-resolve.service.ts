import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITbBoard } from '../tb-board.model';
import { TbBoardService } from '../service/tb-board.service';

@Injectable({ providedIn: 'root' })
export class TbBoardRoutingResolveService implements Resolve<ITbBoard | null> {
  constructor(protected service: TbBoardService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITbBoard | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tbBoard: HttpResponse<ITbBoard>) => {
          if (tbBoard.body) {
            return of(tbBoard.body);
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
