import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { UrlComponent } from './list/url.component';
import { UrlDetailComponent } from './detail/url-detail.component';
import { UrlUpdateComponent } from './update/url-update.component';
import UrlResolve from './route/url-routing-resolve.service';

const urlRoute: Routes = [
  {
    path: '',
    component: UrlComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UrlDetailComponent,
    resolve: {
      url: UrlResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UrlUpdateComponent,
    resolve: {
      url: UrlResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UrlUpdateComponent,
    resolve: {
      url: UrlResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default urlRoute;
