import { lazy } from 'react-router-guard';

export default [
  {
    path: '/',
    component: lazy(() => import('./layouts/MainLayout')),
    routes: [
      {
        path: '/',
        exact: true,
        redirect: '/home/1',
      },
      {
        path: '/services',
        redirect: '/services/1',
      },
      {
        path: '/component/1',
        component: lazy(() => import('./pages/Component')),
      },
      {
        path: '/auths/1',
        component: lazy(() => import('./pages/Auths')),
      },
    ],
  },
];
