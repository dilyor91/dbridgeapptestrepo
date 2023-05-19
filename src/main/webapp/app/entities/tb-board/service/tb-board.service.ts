import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITbBoard, NewTbBoard } from '../tb-board.model';

export type PartialUpdateTbBoard = Partial<ITbBoard> & Pick<ITbBoard, 'id'>;

type RestOf<T extends ITbBoard | NewTbBoard> = Omit<T, 'publishedDate' | 'regDate' | 'modDate'> & {
  publishedDate?: string | null;
  regDate?: string | null;
  modDate?: string | null;
};

export type RestTbBoard = RestOf<ITbBoard>;

export type NewRestTbBoard = RestOf<NewTbBoard>;

export type PartialUpdateRestTbBoard = RestOf<PartialUpdateTbBoard>;

export type EntityResponseType = HttpResponse<ITbBoard>;
export type EntityArrayResponseType = HttpResponse<ITbBoard[]>;

@Injectable({ providedIn: 'root' })
export class TbBoardService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tb-boards');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tbBoard: NewTbBoard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tbBoard);
    return this.http
      .post<RestTbBoard>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(tbBoard: ITbBoard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tbBoard);
    return this.http
      .put<RestTbBoard>(`${this.resourceUrl}/${this.getTbBoardIdentifier(tbBoard)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(tbBoard: PartialUpdateTbBoard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tbBoard);
    return this.http
      .patch<RestTbBoard>(`${this.resourceUrl}/${this.getTbBoardIdentifier(tbBoard)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTbBoard>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTbBoard[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTbBoardIdentifier(tbBoard: Pick<ITbBoard, 'id'>): number {
    return tbBoard.id;
  }

  compareTbBoard(o1: Pick<ITbBoard, 'id'> | null, o2: Pick<ITbBoard, 'id'> | null): boolean {
    return o1 && o2 ? this.getTbBoardIdentifier(o1) === this.getTbBoardIdentifier(o2) : o1 === o2;
  }

  addTbBoardToCollectionIfMissing<Type extends Pick<ITbBoard, 'id'>>(
    tbBoardCollection: Type[],
    ...tbBoardsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tbBoards: Type[] = tbBoardsToCheck.filter(isPresent);
    if (tbBoards.length > 0) {
      const tbBoardCollectionIdentifiers = tbBoardCollection.map(tbBoardItem => this.getTbBoardIdentifier(tbBoardItem)!);
      const tbBoardsToAdd = tbBoards.filter(tbBoardItem => {
        const tbBoardIdentifier = this.getTbBoardIdentifier(tbBoardItem);
        if (tbBoardCollectionIdentifiers.includes(tbBoardIdentifier)) {
          return false;
        }
        tbBoardCollectionIdentifiers.push(tbBoardIdentifier);
        return true;
      });
      return [...tbBoardsToAdd, ...tbBoardCollection];
    }
    return tbBoardCollection;
  }

  protected convertDateFromClient<T extends ITbBoard | NewTbBoard | PartialUpdateTbBoard>(tbBoard: T): RestOf<T> {
    return {
      ...tbBoard,
      publishedDate: tbBoard.publishedDate?.toJSON() ?? null,
      regDate: tbBoard.regDate?.toJSON() ?? null,
      modDate: tbBoard.modDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTbBoard: RestTbBoard): ITbBoard {
    return {
      ...restTbBoard,
      publishedDate: restTbBoard.publishedDate ? dayjs(restTbBoard.publishedDate) : undefined,
      regDate: restTbBoard.regDate ? dayjs(restTbBoard.regDate) : undefined,
      modDate: restTbBoard.modDate ? dayjs(restTbBoard.modDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTbBoard>): HttpResponse<ITbBoard> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTbBoard[]>): HttpResponse<ITbBoard[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
