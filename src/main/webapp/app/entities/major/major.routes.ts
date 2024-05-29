import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MajorComponent } from './list/major.component';
import { MajorDetailComponent } from './detail/major-detail.component';
import { MajorUpdateComponent } from './update/major-update.component';
import MajorResolve from './route/major-routing-resolve.service';

const majorRoute: Routes = [
  {
    path: '',
    component: MajorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MajorDetailComponent,
    resolve: {
      major: MajorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MajorUpdateComponent,
    resolve: {
      major: MajorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MajorUpdateComponent,
    resolve: {
      major: MajorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default majorRoute;
