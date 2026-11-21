import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { SidebarComponent } from './sidebar/sidebar.component';
import { MetricCardComponent } from './metric-card/metric-card.component';
import { SectorChartComponent } from './sector-chart/sector-chart.component';
import { TrendChartComponent } from './trend-chart/trend-chart.component';
import { DashboardData } from '@models/dashboard.model';
import { AdministradorService } from '@service/administrador.service';
import { CadastrarQuestionario } from './cadastrar-questionario/cadastrar-questionario';
import { RespostasIndividuaisComponent } from './respostas-individuais/respostas-individuais.component';
import { AnalisePorSetorComponent } from './analise-por-setor/analise-por-setor.component';

@Component({
  selector: 'app-administrador',
  standalone: true,
  imports: [
    CommonModule,
    SidebarComponent,
    MetricCardComponent,
    SectorChartComponent,
    TrendChartComponent,
    CadastrarQuestionario,
    RespostasIndividuaisComponent,
    AnalisePorSetorComponent
  ],
  templateUrl: './administrador.html',
  styleUrls: ['./administrador.css'] // Mantido conforme o teu ficheiro original
})
export class Administrador implements OnInit {
  private administradorService = inject(AdministradorService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  dashboardData?: DashboardData;
  isLoading = true;
  
  nomeAdmin: string = 'Administrador';
  emailAdmin: string = 'admin@empresa.com';

  // Controlo da aba ativa no painel (Visão Geral, Respostas, Setor, Relatórios, Cadastrar Questionário)
  abaAtiva: string = 'visao-geral';

  ngOnInit(): void {
    const dadosUsuario = localStorage.getItem('usuarioLogado');
    
    if (dadosUsuario) {
      const usuario = JSON.parse(dadosUsuario);
      const idAdministrador = usuario.id;

      this.nomeAdmin = usuario.nome;
      this.emailAdmin = usuario.email;

      this.administradorService.buscarPorId(idAdministrador).subscribe({
        next: (data: any) => {
          this.dashboardData = data;
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (err: any) => {
          console.error('Erro ao conectar com o backend Java:', err);
          this.isLoading = false; 
          this.cdr.detectChanges();
        }
      });
    } else {
      void this.router.navigate(['/login']);
    }
  }

  // Método que altera a aba ativa com base no evento emitido pela sidebar
  selecionarAba(novaAba: string): void {
    this.abaAtiva = novaAba;
  }

  handleLogout(): void {
    localStorage.removeItem('usuarioLogado');
    void this.router.navigate(['']);
  }
}

//$2a$10$nRFMqTVY2f7Ouy8G6iXS4utdcDcIqNq0b8o/PACSN5tccO8goGWie