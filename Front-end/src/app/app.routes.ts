import { Routes } from '@angular/router';
import { Agradecimento } from './agradecimento/agradecimento';
import { Administrador } from './administrador/administrador';
import { CadastrarQuestionario } from './administrador/cadastrar-questionario/cadastrar-questionario';
import { Login } from './login/login';
import { Questionario } from './questionario/questionario'; // Importação correta

export const routes: Routes = [
  { path: '', component: Login },
  { path: 'questionario', component: Questionario }, // Rota ativa
  { path: 'agradecimento', component: Agradecimento },
  { path: 'administrador', component: Administrador },
  { path: 'admin/visao-geral', component: Administrador },
  { path: 'admin/respostas-individuais', component: Administrador },
  { path: 'admin/analise-setor', component: Administrador },
  { path: 'admin/relatorios', component: Administrador },
  { path: 'admin/cadastrar-questionario', component: CadastrarQuestionario },
  { path: '**', redirectTo: '' },
];
