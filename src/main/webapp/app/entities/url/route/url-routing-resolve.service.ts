import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUrl } from '../url.model';
import { UrlService } from '../service/url.service';

const urlResolve = (route: ActivatedRouteSnapshot): Observable<null | IUrl> => {
  const id = route.params['id'];
  if (id) {
    return inject(UrlService)
      .find(id)
      .pipe(
        mergeMap((url: HttpResponse<IUrl>) => {
          if (url.body) {
            return of(url.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default urlResolve;
