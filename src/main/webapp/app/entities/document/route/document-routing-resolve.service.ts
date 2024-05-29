import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocument } from '../document.model';
import { DocumentService } from '../service/document.service';

const documentResolve = (route: ActivatedRouteSnapshot): Observable<null | IDocument> => {
  const id = route.params['id'];
  if (id) {
    return inject(DocumentService)
      .find(id)
      .pipe(
        mergeMap((document: HttpResponse<IDocument>) => {
          if (document.body) {
            return of(document.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default documentResolve;
