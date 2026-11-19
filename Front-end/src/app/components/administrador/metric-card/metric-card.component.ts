import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

@Component({
  selector: 'app-metric-card',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="card">
      <span class="card-label">{{ label }}</span>
      <div class="card-value" [ngClass]="variant">{{ value }}</div>
      <span class="card-subtext">{{ subtext }}</span>
    </div>
  `,
  styles: [`
    .card {
      background: white;
      padding: 20px;
      border-radius: 8px;
      border: 1px solid #e2e8f0;
      box-shadow: 0 1px 2px rgba(0,0,0,0.03);
    }
    .card-label {
      font-size: 11px;
      font-weight: 700;
      color: #64748b;
      letter-spacing: 0.05em;
    }
    .card-value {
      font-size: 32px;
      font-weight: 800;
      margin: 8px 0;
      color: #0f172a;
    }
    .card-value.danger { color: #dc2626; }
    .card-value.warning { color: #dc2626; }
    .card-value.primary { color: #2563eb; }
    .card-subtext {
      font-size: 12px;
      color: #64748b;
    }
  `]
})
export class MetricCardComponent {
  @Input({ required: true }) label!: string;
  @Input({ required: true }) value!: string | number | null | undefined;
  @Input() subtext: string = '';
  @Input() variant: 'default' | 'danger' | 'warning' | 'primary' = 'default';
}

