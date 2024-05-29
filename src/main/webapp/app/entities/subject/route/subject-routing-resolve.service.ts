import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISubject } from '../subject.model';
import { SubjectService } from '../service/subject.service';

const subjectResolve = (route: ActivatedRouteSnapshot): Observable<null | ISubject> => {
  const id = route.params['id'];
  if (id) {
    return inject(SubjectService)
      .find(id)
      .pipe(
        mergeMap((subject: HttpResponse<ISubject>) => {
          if (subject.body) {
            return of(subject.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default subjectResolve;
