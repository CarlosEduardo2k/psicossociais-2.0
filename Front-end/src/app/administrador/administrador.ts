import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
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
  private administradorService = inject(AdministradorService);

  dashboardData?: DashboardData;
  isLoading = true;

  ngOnInit(): void {
    const idAdministrador = 1;

    this.administradorService.buscarPorId(1).subscribe({
      next: (dados) => {
        this.administrador = dados; // Garante que a variável recebe os dados
        this.carregando = false;    // Desativa o indicador de carregamento
      },
      error: (err: any) => {
        console.error('Erro ao conectar com o backend Java:', err);
        this.isLoading = false;
      }
    });
  }

  handleLogout():void {
    console.log('Realizando logout do usuário...');
  }
}