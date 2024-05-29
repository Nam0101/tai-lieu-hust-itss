import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IComments, NewComments } from '../comments.model';

export type PartialUpdateComments = Partial<IComments> & Pick<IComments, 'id'>;

type RestOf<T extends IComments | NewComments> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestComments = RestOf<IComments>;

export type NewRestComments = RestOf<NewComments>;

export type PartialUpdateRestComments = RestOf<PartialUpdateComments>;

export type EntityResponseType = HttpResponse<IComments>;
export type EntityArrayResponseType = HttpResponse<IComments[]>;

@Injectable({ providedIn: 'root' })
export class CommentsService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/comments');

  create(comments: NewComments): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comments);
    return this.http
      .post<RestComments>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(comments: IComments): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comments);
    return this.http
      .put<RestComments>(`${this.resourceUrl}/${this.getCommentsIdentifier(comments)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(comments: PartialUpdateComments): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comments);
    return this.http
      .patch<RestComments>(`${this.resourceUrl}/${this.getCommentsIdentifier(comments)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestComments>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestComments[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCommentsIdentifier(comments: Pick<IComments, 'id'>): number {
    return comments.id;
  }

  compareComments(o1: Pick<IComments, 'id'> | null, o2: Pick<IComments, 'id'> | null): boolean {
    return o1 && o2 ? this.getCommentsIdentifier(o1) === this.getCommentsIdentifier(o2) : o1 === o2;
  }

  addCommentsToCollectionIfMissing<Type extends Pick<IComments, 'id'>>(
    commentsCollection: Type[],
    ...commentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const comments: Type[] = commentsToCheck.filter(isPresent);
    if (comments.length > 0) {
      const commentsCollectionIdentifiers = commentsCollection.map(commentsItem => this.getCommentsIdentifier(commentsItem));
      const commentsToAdd = comments.filter(commentsItem => {
        const commentsIdentifier = this.getCommentsIdentifier(commentsItem);
        if (commentsCollectionIdentifiers.includes(commentsIdentifier)) {
          return false;
        }
        commentsCollectionIdentifiers.push(commentsIdentifier);
        return true;
      });
      return [...commentsToAdd, ...commentsCollection];
    }
    return commentsCollection;
  }

  protected convertDateFromClient<T extends IComments | NewComments | PartialUpdateComments>(comments: T): RestOf<T> {
    return {
      ...comments,
      createdAt: comments.createdAt?.toJSON() ?? null,
      updatedAt: comments.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restComments: RestComments): IComments {
    return {
      ...restComments,
      createdAt: restComments.createdAt ? dayjs(restComments.createdAt) : undefined,
      updatedAt: restComments.updatedAt ? dayjs(restComments.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestComments>): HttpResponse<IComments> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestComments[]>): HttpResponse<IComments[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
