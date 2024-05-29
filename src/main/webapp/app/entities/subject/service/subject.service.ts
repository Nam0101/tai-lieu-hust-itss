import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubject, NewSubject } from '../subject.model';

export type PartialUpdateSubject = Partial<ISubject> & Pick<ISubject, 'id'>;

export type EntityResponseType = HttpResponse<ISubject>;
export type EntityArrayResponseType = HttpResponse<ISubject[]>;

@Injectable({ providedIn: 'root' })
export class SubjectService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/subjects');

  create(subject: NewSubject): Observable<EntityResponseType> {
    return this.http.post<ISubject>(this.resourceUrl, subject, { observe: 'response' });
  }

  update(subject: ISubject): Observable<EntityResponseType> {
    return this.http.put<ISubject>(`${this.resourceUrl}/${this.getSubjectIdentifier(subject)}`, subject, { observe: 'response' });
  }

  partialUpdate(subject: PartialUpdateSubject): Observable<EntityResponseType> {
    return this.http.patch<ISubject>(`${this.resourceUrl}/${this.getSubjectIdentifier(subject)}`, subject, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubject>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubject[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSubjectIdentifier(subject: Pick<ISubject, 'id'>): number {
    return subject.id;
  }

  compareSubject(o1: Pick<ISubject, 'id'> | null, o2: Pick<ISubject, 'id'> | null): boolean {
    return o1 && o2 ? this.getSubjectIdentifier(o1) === this.getSubjectIdentifier(o2) : o1 === o2;
  }

  addSubjectToCollectionIfMissing<Type extends Pick<ISubject, 'id'>>(
    subjectCollection: Type[],
    ...subjectsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const subjects: Type[] = subjectsToCheck.filter(isPresent);
    if (subjects.length > 0) {
      const subjectCollectionIdentifiers = subjectCollection.map(subjectItem => this.getSubjectIdentifier(subjectItem));
      const subjectsToAdd = subjects.filter(subjectItem => {
        const subjectIdentifier = this.getSubjectIdentifier(subjectItem);
        if (subjectCollectionIdentifiers.includes(subjectIdentifier)) {
          return false;
        }
        subjectCollectionIdentifiers.push(subjectIdentifier);
        return true;
      });
      return [...subjectsToAdd, ...subjectCollection];
    }
    return subjectCollection;
  }
}
