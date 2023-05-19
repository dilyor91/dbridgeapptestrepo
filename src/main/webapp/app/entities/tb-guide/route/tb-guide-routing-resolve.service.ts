import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITbGuide } from '../tb-guide.model';
import { TbGuideService } from '../service/tb-guide.service';

@Injectable({ providedIn: 'root' })
export class TbGuideRoutingResolveService implements Resolve<ITbGuide | null> {
  constructor(protected service: TbGuideService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITbGuide | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tbGuide: HttpResponse<ITbGuide>) => {
          if (tbGuide.body) {
            return of(tbGuide.body);
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
