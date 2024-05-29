import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'Authorities' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'major',
    data: { pageTitle: 'Majors' },
    loadChildren: () => import('./major/major.routes'),
  },
  {
    path: 'subject',
    data: { pageTitle: 'Subjects' },
    loadChildren: () => import('./subject/subject.routes'),
  },
  {
    path: 'document',
    data: { pageTitle: 'Documents' },
    loadChildren: () => import('./document/document.routes'),
  },
  {
    path: 'url',
    data: { pageTitle: 'Urls' },
    loadChildren: () => import('./url/url.routes'),
  },
  {
    path: 'comments',
    data: { pageTitle: 'Comments' },
    loadChildren: () => import('./comments/comments.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
