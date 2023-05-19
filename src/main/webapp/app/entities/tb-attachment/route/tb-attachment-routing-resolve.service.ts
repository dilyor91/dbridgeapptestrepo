import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITbAttachment } from '../tb-attachment.model';
import { TbAttachmentService } from '../service/tb-attachment.service';

@Injectable({ providedIn: 'root' })
export class TbAttachmentRoutingResolveService implements Resolve<ITbAttachment | null> {
  constructor(protected service: TbAttachmentService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITbAttachment | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tbAttachment: HttpResponse<ITbAttachment>) => {
          if (tbAttachment.body) {
            return of(tbAttachment.body);
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
