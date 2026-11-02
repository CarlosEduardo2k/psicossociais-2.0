import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { SidebarComponent } from '../components/sidebar/sidebar.component';
import { MetricCardComponent } from '../components/metric-card/metric-card.component';
import { SectorChartComponent } from '../components/sector-chart/sector-chart.component';
import { TrendChartComponent } from '../components/trend-chart/trend-chart.component';
import { DashboardData } from '../models/dashboard.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    SidebarComponent,
    MetricCardComponent,
    SectorChartComponent,
    TrendChartComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  private http = inject(HttpClient);

  // Altere para a URL real da sua API em Java
  private apiUrl = 'http://localhost:8080/api/dashboard';

  dashboardData?: DashboardData;
  isLoading = true;

  ngOnInit(): void {
    this.http.get<DashboardData>(this.apiUrl).subscribe({
      next: (data) => {
        this.dashboardData = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erro ao conectar com o backend Java:', err);
        this.isLoading = false;
      }
    });
  }

  handleLogout(): void {
    console.log('Realizando logout do usuário...');
  }
}