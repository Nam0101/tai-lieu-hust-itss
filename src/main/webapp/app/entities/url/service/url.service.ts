import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUrl, NewUrl } from '../url.model';

export type PartialUpdateUrl = Partial<IUrl> & Pick<IUrl, 'id'>;

export type EntityResponseType = HttpResponse<IUrl>;
export type EntityArrayResponseType = HttpResponse<IUrl[]>;

@Injectable({ providedIn: 'root' })
export class UrlService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/urls');

  create(url: NewUrl): Observable<EntityResponseType> {
    return this.http.post<IUrl>(this.resourceUrl, url, { observe: 'response' });
  }

  update(url: IUrl): Observable<EntityResponseType> {
    return this.http.put<IUrl>(`${this.resourceUrl}/${this.getUrlIdentifier(url)}`, url, { observe: 'response' });
  }

  partialUpdate(url: PartialUpdateUrl): Observable<EntityResponseType> {
    return this.http.patch<IUrl>(`${this.resourceUrl}/${this.getUrlIdentifier(url)}`, url, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUrl>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUrl[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUrlIdentifier(url: Pick<IUrl, 'id'>): number {
    return url.id;
  }

  compareUrl(o1: Pick<IUrl, 'id'> | null, o2: Pick<IUrl, 'id'> | null): boolean {
    return o1 && o2 ? this.getUrlIdentifier(o1) === this.getUrlIdentifier(o2) : o1 === o2;
  }

  addUrlToCollectionIfMissing<Type extends Pick<IUrl, 'id'>>(urlCollection: Type[], ...urlsToCheck: (Type | null | undefined)[]): Type[] {
    const urls: Type[] = urlsToCheck.filter(isPresent);
    if (urls.length > 0) {
      const urlCollectionIdentifiers = urlCollection.map(urlItem => this.getUrlIdentifier(urlItem));
      const urlsToAdd = urls.filter(urlItem => {
        const urlIdentifier = this.getUrlIdentifier(urlItem);
        if (urlCollectionIdentifiers.includes(urlIdentifier)) {
          return false;
        }
        urlCollectionIdentifiers.push(urlIdentifier);
        return true;
      });
      return [...urlsToAdd, ...urlCollection];
    }
    return urlCollection;
  }
}
