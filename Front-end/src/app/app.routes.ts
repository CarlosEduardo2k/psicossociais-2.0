import { Routes } from '@angular/router';
import { Agradecimento } from './agradecimento/agradecimento';
import { Administrador } from './administrador/administrador';
import { Login } from './login/login';

export const routes: Routes = [
  { path: '', component: Login },
  { path: 'agradecimento', component: Agradecimento },
  { path: 'administrador', component: Administrador },
  { path: '**', redirectTo: '' },
];
