import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-analise-por-setor',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './analise-por-setor.component.html',
  styleUrl: './analise-por-setor.component.css'
})
export class AnalisePorSetorComponent {
  setoresAnalise = [
    { nome: 'RH', baixo: 1, medio: 2, alto: 2, critico: 0, total: 5, status: 'Monitorar' },
    { nome: 'TI', baixo: 3, medio: 1, alto: 0, critico: 0, total: 4, status: 'Estável' },
    { nome: 'Financeiro', baixo: 2, medio: 2, alto: 0, critico: 0, total: 4, status: 'Estável' },
    { nome: 'Operações', baixo: 0, medio: 0, alto: 1, critico: 2, total: 3, status: 'Atenção Crítica' },
    { nome: 'Comercial', baixo: 0, medio: 2, alto: 1, critico: 0, total: 3, status: 'Monitorar' }
  ];

  obterClasseStatus(status: string): string {
    switch (status.toLowerCase()) {
      case 'estável': return 'bg-blue-50 text-blue-700 border border-blue-200';
      case 'monitorar': return 'bg-amber-50 text-amber-700 border border-amber-200';
      case 'atenção crítica': return 'bg-purple-50 text-purple-700 border border-purple-200';
      default: return 'bg-gray-100 text-gray-700';
    }
  }
}