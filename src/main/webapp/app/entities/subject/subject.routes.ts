import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SubjectComponent } from './list/subject.component';
import { SubjectDetailComponent } from './detail/subject-detail.component';
import { SubjectUpdateComponent } from './update/subject-update.component';
import SubjectResolve from './route/subject-routing-resolve.service';

const subjectRoute: Routes = [
  {
    path: '',
    component: SubjectComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubjectDetailComponent,
    resolve: {
      subject: SubjectResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubjectUpdateComponent,
    resolve: {
      subject: SubjectResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubjectUpdateComponent,
    resolve: {
      subject: SubjectResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default subjectRoute;
