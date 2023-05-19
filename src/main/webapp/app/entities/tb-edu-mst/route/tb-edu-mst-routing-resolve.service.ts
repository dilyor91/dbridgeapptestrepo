import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITbEduMst } from '../tb-edu-mst.model';
import { TbEduMstService } from '../service/tb-edu-mst.service';

@Injectable({ providedIn: 'root' })
export class TbEduMstRoutingResolveService implements Resolve<ITbEduMst | null> {
  constructor(protected service: TbEduMstService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITbEduMst | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tbEduMst: HttpResponse<ITbEduMst>) => {
          if (tbEduMst.body) {
            return of(tbEduMst.body);
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
