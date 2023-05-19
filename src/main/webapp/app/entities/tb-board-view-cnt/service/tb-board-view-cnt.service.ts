import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITbBoardViewCnt, NewTbBoardViewCnt } from '../tb-board-view-cnt.model';

export type PartialUpdateTbBoardViewCnt = Partial<ITbBoardViewCnt> & Pick<ITbBoardViewCnt, 'id'>;

export type EntityResponseType = HttpResponse<ITbBoardViewCnt>;
export type EntityArrayResponseType = HttpResponse<ITbBoardViewCnt[]>;

@Injectable({ providedIn: 'root' })
export class TbBoardViewCntService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tb-board-view-cnts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tbBoardViewCnt: NewTbBoardViewCnt): Observable<EntityResponseType> {
    return this.http.post<ITbBoardViewCnt>(this.resourceUrl, tbBoardViewCnt, { observe: 'response' });
  }

  update(tbBoardViewCnt: ITbBoardViewCnt): Observable<EntityResponseType> {
    return this.http.put<ITbBoardViewCnt>(`${this.resourceUrl}/${this.getTbBoardViewCntIdentifier(tbBoardViewCnt)}`, tbBoardViewCnt, {
      observe: 'response',
    });
  }

  partialUpdate(tbBoardViewCnt: PartialUpdateTbBoardViewCnt): Observable<EntityResponseType> {
    return this.http.patch<ITbBoardViewCnt>(`${this.resourceUrl}/${this.getTbBoardViewCntIdentifier(tbBoardViewCnt)}`, tbBoardViewCnt, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITbBoardViewCnt>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITbBoardViewCnt[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTbBoardViewCntIdentifier(tbBoardViewCnt: Pick<ITbBoardViewCnt, 'id'>): number {
    return tbBoardViewCnt.id;
  }

  compareTbBoardViewCnt(o1: Pick<ITbBoardViewCnt, 'id'> | null, o2: Pick<ITbBoardViewCnt, 'id'> | null): boolean {
    return o1 && o2 ? this.getTbBoardViewCntIdentifier(o1) === this.getTbBoardViewCntIdentifier(o2) : o1 === o2;
  }

  addTbBoardViewCntToCollectionIfMissing<Type extends Pick<ITbBoardViewCnt, 'id'>>(
    tbBoardViewCntCollection: Type[],
    ...tbBoardViewCntsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tbBoardViewCnts: Type[] = tbBoardViewCntsToCheck.filter(isPresent);
    if (tbBoardViewCnts.length > 0) {
      const tbBoardViewCntCollectionIdentifiers = tbBoardViewCntCollection.map(
        tbBoardViewCntItem => this.getTbBoardViewCntIdentifier(tbBoardViewCntItem)!
      );
      const tbBoardViewCntsToAdd = tbBoardViewCnts.filter(tbBoardViewCntItem => {
        const tbBoardViewCntIdentifier = this.getTbBoardViewCntIdentifier(tbBoardViewCntItem);
        if (tbBoardViewCntCollectionIdentifiers.includes(tbBoardViewCntIdentifier)) {
          return false;
        }
        tbBoardViewCntCollectionIdentifiers.push(tbBoardViewCntIdentifier);
        return true;
      });
      return [...tbBoardViewCntsToAdd, ...tbBoardViewCntCollection];
    }
    return tbBoardViewCntCollection;
  }
}
