import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITbAttachment, NewTbAttachment } from '../tb-attachment.model';

export type PartialUpdateTbAttachment = Partial<ITbAttachment> & Pick<ITbAttachment, 'id'>;

type RestOf<T extends ITbAttachment | NewTbAttachment> = Omit<T, 'regDate' | 'modDate'> & {
  regDate?: string | null;
  modDate?: string | null;
};

export type RestTbAttachment = RestOf<ITbAttachment>;

export type NewRestTbAttachment = RestOf<NewTbAttachment>;

export type PartialUpdateRestTbAttachment = RestOf<PartialUpdateTbAttachment>;

export type EntityResponseType = HttpResponse<ITbAttachment>;
export type EntityArrayResponseType = HttpResponse<ITbAttachment[]>;

@Injectable({ providedIn: 'root' })
export class TbAttachmentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tb-attachments');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tbAttachment: NewTbAttachment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tbAttachment);
    return this.http
      .post<RestTbAttachment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(tbAttachment: ITbAttachment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tbAttachment);
    return this.http
      .put<RestTbAttachment>(`${this.resourceUrl}/${this.getTbAttachmentIdentifier(tbAttachment)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(tbAttachment: PartialUpdateTbAttachment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tbAttachment);
    return this.http
      .patch<RestTbAttachment>(`${this.resourceUrl}/${this.getTbAttachmentIdentifier(tbAttachment)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTbAttachment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTbAttachment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTbAttachmentIdentifier(tbAttachment: Pick<ITbAttachment, 'id'>): number {
    return tbAttachment.id;
  }

  compareTbAttachment(o1: Pick<ITbAttachment, 'id'> | null, o2: Pick<ITbAttachment, 'id'> | null): boolean {
    return o1 && o2 ? this.getTbAttachmentIdentifier(o1) === this.getTbAttachmentIdentifier(o2) : o1 === o2;
  }

  addTbAttachmentToCollectionIfMissing<Type extends Pick<ITbAttachment, 'id'>>(
    tbAttachmentCollection: Type[],
    ...tbAttachmentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tbAttachments: Type[] = tbAttachmentsToCheck.filter(isPresent);
    if (tbAttachments.length > 0) {
      const tbAttachmentCollectionIdentifiers = tbAttachmentCollection.map(
        tbAttachmentItem => this.getTbAttachmentIdentifier(tbAttachmentItem)!
      );
      const tbAttachmentsToAdd = tbAttachments.filter(tbAttachmentItem => {
        const tbAttachmentIdentifier = this.getTbAttachmentIdentifier(tbAttachmentItem);
        if (tbAttachmentCollectionIdentifiers.includes(tbAttachmentIdentifier)) {
          return false;
        }
        tbAttachmentCollectionIdentifiers.push(tbAttachmentIdentifier);
        return true;
      });
      return [...tbAttachmentsToAdd, ...tbAttachmentCollection];
    }
    return tbAttachmentCollection;
  }

  protected convertDateFromClient<T extends ITbAttachment | NewTbAttachment | PartialUpdateTbAttachment>(tbAttachment: T): RestOf<T> {
    return {
      ...tbAttachment,
      regDate: tbAttachment.regDate?.toJSON() ?? null,
      modDate: tbAttachment.modDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTbAttachment: RestTbAttachment): ITbAttachment {
    return {
      ...restTbAttachment,
      regDate: restTbAttachment.regDate ? dayjs(restTbAttachment.regDate) : undefined,
      modDate: restTbAttachment.modDate ? dayjs(restTbAttachment.modDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTbAttachment>): HttpResponse<ITbAttachment> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTbAttachment[]>): HttpResponse<ITbAttachment[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
