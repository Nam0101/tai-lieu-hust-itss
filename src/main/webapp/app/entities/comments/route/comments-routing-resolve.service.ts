import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IComments } from '../comments.model';
import { CommentsService } from '../service/comments.service';

const commentsResolve = (route: ActivatedRouteSnapshot): Observable<null | IComments> => {
  const id = route.params['id'];
  if (id) {
    return inject(CommentsService)
      .find(id)
      .pipe(
        mergeMap((comments: HttpResponse<IComments>) => {
          if (comments.body) {
            return of(comments.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default commentsResolve;
