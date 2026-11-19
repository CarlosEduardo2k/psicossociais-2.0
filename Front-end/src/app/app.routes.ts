import { Routes } from '@angular/router';
import { Agradecimento } from '@components/agradecimento/agradecimento';
import { Administrador } from '@components/administrador/administrador';
import { CadastrarQuestionario } from '@components/administrador/cadastrar-questionario/cadastrar-questionario';
import { Login } from '@components/login/login';
import { Questionario } from '@components/questionario/questionario';
import { EmConstrucao } from '@components/em-construcao';

export const routes: Routes = [
  { path: '', component: Login },
  { path: 'questionario', component: Questionario },
  { path: 'agradecimento', component: Agradecimento },
  { path: 'administrador', component: Administrador },
  { path: 'admin/visao-geral', component: Administrador },
  { path: 'admin/respostas-individuais', component: Administrador },
  { path: 'admin/analise-setor', component: Administrador },
  { path: 'admin/relatorios', component: Administrador },
  { path: 'admin/cadastrar-questionario', component: CadastrarQuestionario },
  { path: 'em-construcao', component: EmConstrucao },
  { path: '**', redirectTo: 'em-construcao' }
];
