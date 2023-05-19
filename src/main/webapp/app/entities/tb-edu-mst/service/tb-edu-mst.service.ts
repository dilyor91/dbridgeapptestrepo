import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITbEduMst, NewTbEduMst } from '../tb-edu-mst.model';

export type PartialUpdateTbEduMst = Partial<ITbEduMst> & Pick<ITbEduMst, 'id'>;

export type EntityResponseType = HttpResponse<ITbEduMst>;
export type EntityArrayResponseType = HttpResponse<ITbEduMst[]>;

@Injectable({ providedIn: 'root' })
export class TbEduMstService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tb-edu-msts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tbEduMst: NewTbEduMst): Observable<EntityResponseType> {
    return this.http.post<ITbEduMst>(this.resourceUrl, tbEduMst, { observe: 'response' });
  }

  update(tbEduMst: ITbEduMst): Observable<EntityResponseType> {
    return this.http.put<ITbEduMst>(`${this.resourceUrl}/${this.getTbEduMstIdentifier(tbEduMst)}`, tbEduMst, { observe: 'response' });
  }

  partialUpdate(tbEduMst: PartialUpdateTbEduMst): Observable<EntityResponseType> {
    return this.http.patch<ITbEduMst>(`${this.resourceUrl}/${this.getTbEduMstIdentifier(tbEduMst)}`, tbEduMst, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITbEduMst>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITbEduMst[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTbEduMstIdentifier(tbEduMst: Pick<ITbEduMst, 'id'>): number {
    return tbEduMst.id;
  }

  compareTbEduMst(o1: Pick<ITbEduMst, 'id'> | null, o2: Pick<ITbEduMst, 'id'> | null): boolean {
    return o1 && o2 ? this.getTbEduMstIdentifier(o1) === this.getTbEduMstIdentifier(o2) : o1 === o2;
  }

  addTbEduMstToCollectionIfMissing<Type extends Pick<ITbEduMst, 'id'>>(
    tbEduMstCollection: Type[],
    ...tbEduMstsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tbEduMsts: Type[] = tbEduMstsToCheck.filter(isPresent);
    if (tbEduMsts.length > 0) {
      const tbEduMstCollectionIdentifiers = tbEduMstCollection.map(tbEduMstItem => this.getTbEduMstIdentifier(tbEduMstItem)!);
      const tbEduMstsToAdd = tbEduMsts.filter(tbEduMstItem => {
        const tbEduMstIdentifier = this.getTbEduMstIdentifier(tbEduMstItem);
        if (tbEduMstCollectionIdentifiers.includes(tbEduMstIdentifier)) {
          return false;
        }
        tbEduMstCollectionIdentifiers.push(tbEduMstIdentifier);
        return true;
      });
      return [...tbEduMstsToAdd, ...tbEduMstCollection];
    }
    return tbEduMstCollection;
  }
}
