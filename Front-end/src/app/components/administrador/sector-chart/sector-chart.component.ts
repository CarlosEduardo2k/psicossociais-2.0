import { Component, Input, OnChanges } from '@angular/core';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';

@Component({
  selector: 'app-sector-chart',
  standalone: true,
  imports: [BaseChartDirective],
  template: `
    <div class="chart-card">
      <h3>NÍVEL DE RISCO POR SETOR</h3>
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
export class SectorChartComponent implements OnChanges {
  @Input() setores: string[] = [];
  @Input() valores: number[] = [];

  public chartType: ChartType = 'bar';
  public chartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { display: false }
    },
    scales: {
      y: { min: 0, max: 5, ticks: { stepSize: 1 } },
      x: { grid: { display: false } }
    }
  };

  public chartData: ChartData<'bar'> = { labels: [], datasets: [] };

  ngOnChanges(): void {
    this.chartData = {
      labels: this.setores,
      datasets: [
        {
          data: this.valores,
          backgroundColor: '#2563eb',
          borderRadius: 4,
          barThickness: 24
        }
      ]
    };
  }
}