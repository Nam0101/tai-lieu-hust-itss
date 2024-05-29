import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CommentsComponent } from './list/comments.component';
import { CommentsDetailComponent } from './detail/comments-detail.component';
import { CommentsUpdateComponent } from './update/comments-update.component';
import CommentsResolve from './route/comments-routing-resolve.service';

const commentsRoute: Routes = [
  {
    path: '',
    component: CommentsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommentsDetailComponent,
    resolve: {
      comments: CommentsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommentsUpdateComponent,
    resolve: {
      comments: CommentsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommentsUpdateComponent,
    resolve: {
      comments: CommentsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default commentsRoute;
