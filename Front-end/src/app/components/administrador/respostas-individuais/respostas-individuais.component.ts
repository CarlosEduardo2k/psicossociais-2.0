import { Component, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-respostas-individuais',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './respostas-individuais.component.html',
  styleUrl: './respostas-individuais.component.css',
  encapsulation: ViewEncapsulation.None
})

export class RespostasIndividuaisComponent {
  termoBusca: string = '';
  setorSelecionado: string = 'Todos';
  riscoSelecionado: string = 'Todos';

  setores: string[] = ['Todos', 'RH', 'TI', 'Financeiro', 'Operações', 'Comercial'];
  riscos: string[] = ['Todos', 'Baixo', 'Médio', 'Alto', 'Crítico'];

  // Dados mockados para teste imediato
  listaRespostas: any[] = [
    { nome: 'Ana Paula Ferreira', setor: 'RH', data: '12/09/2025', estresse: '4/5', sono: '3/5', exaustao: '4/5', concentracao: '3/5', sintomas: '2/5', risco: 'Alto' },
    { nome: 'Carlos Mendes', setor: 'TI', data: '12/09/2025', estresse: '2/5', sono: '4/5', exaustao: '2/5', concentracao: '4/5', sintomas: '1/5', risco: 'Baixo' },
    { nome: 'Fernanda Lima', setor: 'Financeiro', data: '11/09/2025', estresse: '3/5', sono: '3/5', exaustao: '3/5', concentracao: '3/5', sintomas: '3/5', risco: 'Médio' },
    { nome: 'Roberto Costa', setor: 'Operações', data: '11/09/2025', estresse: '5/5', sono: '2/5', exaustao: '5/5', concentracao: '2/5', sintomas: '4/5', risco: 'Crítico' },
    { nome: 'Juliana Ribeiro', setor: 'RH', data: '10/09/2025', estresse: '3/5', sono: '4/5', exaustao: '3/5', concentracao: '4/5', sintomas: '2/5', risco: 'Médio' },
    { nome: 'Marcos Oliveira', setor: 'TI', data: '10/09/2025', estresse: '2/5', sono: '5/5', exaustao: '2/5', concentracao: '5/5', sintomas: '1/5', risco: 'Baixo' },
    { nome: 'Patricia Santos', setor: 'Comercial', data: '09/09/2025', estresse: '4/5', sono: '3/5', exaustao: '4/5', concentracao: '3/5', sintomas: '3/5', risco: 'Alto' },
    { nome: 'Diego Almeida', setor: 'Operações', data: '09/09/2025', estresse: '5/5', sono: '2/5', exaustao: '4/5', concentracao: '2/5', sintomas: '4/5', risco: 'Crítico' },
    { nome: 'Larissa Pereira', setor: 'Financeiro', data: '08/09/2025', estresse: '2/5', sono: '4/5', exaustao: '2/5', concentracao: '4/5', sintomas: '1/5', risco: 'Baixo' }
  ];

  get listaFiltrada() {
    return this.listaRespostas.filter(item => {
      const matchNome = item.nome.toLowerCase().includes(this.termoBusca.toLowerCase());
      const matchSetor = this.setorSelecionado === 'Todos' || item.setor === this.setorSelecionado;
      const matchRisco = this.riscoSelecionado === 'Todos' || item.risco.toLowerCase() === this.riscoSelecionado.toLowerCase();
      return matchNome && matchSetor && matchRisco;
    });
  }

  obterClasseRisco(risco: string): string {
    switch (risco.toLowerCase()) {
      case 'baixo': return 'badge-baixo';
      case 'médio': return 'badge-medio';
      case 'alto': return 'badge-alto';
      case 'crítico': return 'badge-critico';
      default: return 'bg-gray-100 text-gray-700';
    }
  }
}