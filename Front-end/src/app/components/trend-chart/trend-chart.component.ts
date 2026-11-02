import { Component, Input, OnChanges } from '@angular/core';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';

@Component({
  selector: 'app-trend-chart',
  standalone: true,
  imports: [BaseChartDirective],
  template: `
    <div class="chart-card">
      <h3>TENDÊNCIA — ÚLTIMOS 4 MESES</h3>
      <div class="chart-container">
        <canvas baseChart
          [data]="chartData"
          [options]="chartOptions"
          [type]="chartType">
        </canvas>
      </div>
    </div>
  `,
  styles: [`
    .chart-card {
      background: white;
      padding: 20px;
      border-radius: 8px;
      border: 1px solid #e2e8f0;
      box-shadow: 0 1px 2px rgba(0,0,0,0.03);
    }
    .chart-card h3 {
      font-size: 11px;
      font-weight: 700;
      color: #64748b;
      letter-spacing: 0.05em;
      margin-bottom: 16px;
    }
    .chart-container {
      position: relative;
      height: 280px;
      width: 100%;
    }
  `]
})
export class TrendChartComponent implements OnChanges {
  @Input() meses: string[] = [];
  @Input() estresse: number[] = [];
  @Input() exaustao: number[] = [];
  @Input() qualidadeSono: number[] = [];

  public chartType: ChartType = 'line';
  public chartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { position: 'bottom' }
    },
    scales: {
      y: { min: 1, max: 5, ticks: { stepSize: 1 } }
    }
  };

  public chartData: ChartData<'line'> = { labels: [], datasets: [] };

  ngOnChanges(): void {
    this.chartData = {
      labels: this.meses,
      datasets: [
        {
          data: this.estresse,
          label: 'Estresse',
          borderColor: '#1d4ed8',
          backgroundColor: '#1d4ed8',
          tension: 0.1
        },
        {
          data: this.exaustao,
          label: 'Exaustão',
          borderColor: '#3b82f6',
          backgroundColor: '#3b82f6',
          tension: 0.1
        },
        {
          data: this.qualidadeSono,
          label: 'Qualid. sono',
          borderColor: '#93c5fd',
          backgroundColor: '#93c5fd',
          tension: 0.1
        }
      ]
    };
  }
}