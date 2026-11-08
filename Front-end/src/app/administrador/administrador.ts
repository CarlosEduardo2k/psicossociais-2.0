import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { SidebarComponent } from '../components/sidebar/sidebar.component';
import { MetricCardComponent } from '../components/metric-card/metric-card.component';
import { SectorChartComponent } from '../components/sector-chart/sector-chart.component';
import { TrendChartComponent } from '../components/trend-chart/trend-chart.component';
import { DashboardData } from '../models/dashboard.model';
import { AdministradorService } from '../service/administrador.service';

@Component({
  selector: 'app-administrador',
  standalone: true,
  imports: [
    CommonModule,
    SidebarComponent,
    MetricCardComponent,
    SectorChartComponent,
    TrendChartComponent
  ],
  templateUrl: './administrador.html',
  styleUrls: ['./administrador.css']
})
export class Administrador implements OnInit {
  // aqui ele ver qual usuário está logado
  private administradorService = inject(AdministradorService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  dashboardData?: DashboardData;
  isLoading = true;
  
  nomeAdmin: string = 'Administrador';
  emailAdmin: string = 'admin@empresa.com';

  // Controlo da aba ativa no painel (Visão Geral, Respostas, Setor, Relatórios)
  abaAtiva: string = 'visao-geral';

  ngOnInit(): void {
    const dadosUsuario = localStorage.getItem('usuarioLogado');
    
    if (dadosUsuario) {
      const usuario = JSON.parse(dadosUsuario);
      const idAdministrador = usuario.id || 5; 

      this.nomeAdmin = usuario.nome || 'Administrador';
      this.emailAdmin = usuario.email || 'admin@empresa.com';

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

  handleLogout(): void {
    localStorage.removeItem('usuarioLogado');
    void this.router.navigate(['/login']);
  }
}