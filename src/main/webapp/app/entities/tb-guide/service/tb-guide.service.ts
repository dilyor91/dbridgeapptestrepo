import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITbGuide, NewTbGuide } from '../tb-guide.model';

export type PartialUpdateTbGuide = Partial<ITbGuide> & Pick<ITbGuide, 'id'>;

type RestOf<T extends ITbGuide | NewTbGuide> = Omit<T, 'regDate' | 'modDate'> & {
  regDate?: string | null;
  modDate?: string | null;
};

export type RestTbGuide = RestOf<ITbGuide>;

export type NewRestTbGuide = RestOf<NewTbGuide>;

export type PartialUpdateRestTbGuide = RestOf<PartialUpdateTbGuide>;

export type EntityResponseType = HttpResponse<ITbGuide>;
export type EntityArrayResponseType = HttpResponse<ITbGuide[]>;

@Injectable({ providedIn: 'root' })
export class TbGuideService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tb-guides');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tbGuide: NewTbGuide): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tbGuide);
    return this.http
      .post<RestTbGuide>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(tbGuide: ITbGuide): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tbGuide);
    return this.http
      .put<RestTbGuide>(`${this.resourceUrl}/${this.getTbGuideIdentifier(tbGuide)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(tbGuide: PartialUpdateTbGuide): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tbGuide);
    return this.http
      .patch<RestTbGuide>(`${this.resourceUrl}/${this.getTbGuideIdentifier(tbGuide)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTbGuide>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTbGuide[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTbGuideIdentifier(tbGuide: Pick<ITbGuide, 'id'>): number {
    return tbGuide.id;
  }

  compareTbGuide(o1: Pick<ITbGuide, 'id'> | null, o2: Pick<ITbGuide, 'id'> | null): boolean {
    return o1 && o2 ? this.getTbGuideIdentifier(o1) === this.getTbGuideIdentifier(o2) : o1 === o2;
  }

  addTbGuideToCollectionIfMissing<Type extends Pick<ITbGuide, 'id'>>(
    tbGuideCollection: Type[],
    ...tbGuidesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tbGuides: Type[] = tbGuidesToCheck.filter(isPresent);
    if (tbGuides.length > 0) {
      const tbGuideCollectionIdentifiers = tbGuideCollection.map(tbGuideItem => this.getTbGuideIdentifier(tbGuideItem)!);
      const tbGuidesToAdd = tbGuides.filter(tbGuideItem => {
        const tbGuideIdentifier = this.getTbGuideIdentifier(tbGuideItem);
        if (tbGuideCollectionIdentifiers.includes(tbGuideIdentifier)) {
          return false;
        }
        tbGuideCollectionIdentifiers.push(tbGuideIdentifier);
        return true;
      });
      return [...tbGuidesToAdd, ...tbGuideCollection];
    }
    return tbGuideCollection;
  }

  protected convertDateFromClient<T extends ITbGuide | NewTbGuide | PartialUpdateTbGuide>(tbGuide: T): RestOf<T> {
    return {
      ...tbGuide,
      regDate: tbGuide.regDate?.toJSON() ?? null,
      modDate: tbGuide.modDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTbGuide: RestTbGuide): ITbGuide {
    return {
      ...restTbGuide,
      regDate: restTbGuide.regDate ? dayjs(restTbGuide.regDate) : undefined,
      modDate: restTbGuide.modDate ? dayjs(restTbGuide.modDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTbGuide>): HttpResponse<ITbGuide> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTbGuide[]>): HttpResponse<ITbGuide[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
