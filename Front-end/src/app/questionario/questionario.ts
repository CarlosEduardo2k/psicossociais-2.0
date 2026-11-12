import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-questionario',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './questionario.html',
  styleUrls: ['./questionario.css'],
})
export class Questionario implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly cdRef = inject(ChangeDetectorRef);

  etapas: string[] = [
    'Estresse Ocupacional',
    'Qualidade do Sono',
    'Exaustão Emocional',
    'Capacidade de Concentração',
    'Sintomas Físicos',
  ];

  etapaAtual = 0;
  respostas: { [key: number]: string } = {};

  dadosQuestionario: { pergunta: string; opcoes: string[] }[] = [
    {
      pergunta: 'Com que frequência você se sente sobrecarregado(a) pelas demandas do seu trabalho?',
      opcoes: ['Nunca ou raramente', 'Algumas vezes por mês', 'Algumas vezes por semana', 'Quase todos os dias', 'Todos os dias, o tempo todo'],
    },
    {
      pergunta: 'Como você avalia a qualidade do seu sono nas últimas duas semanas?',
      opcoes: ['Excelente — durmo bem e acordo descansado(a)', 'Boa — pequenas dificuldades ocasionais', 'Regular — tenho dificuldades frequentes', 'Ruim — raramente durmo bem', 'Péssima — insônia ou sono muito interrompido'],
    },
    {
      pergunta: 'Ao final do expediente, com que frequência você se sente emocionalmente esgotado(a)?',
      opcoes: ['Nunca — geralmente termino o dia com energia', 'Raramente — apenas em dias mais intensos', 'Às vezes — cerca de metade dos dias', 'Frequentemente — na maioria dos dias', 'Sempre — sinto-me esgotado(a) todos os dias'],
    },
    {
      pergunta: 'Como você avalia sua capacidade de se concentrar nas tarefas do trabalho ultimamente?',
      opcoes: ['Muito boa — foco pleno sem dificuldades', 'Boa — pequenas distrações pontuais', 'Regular — tenho dificuldade com frequência', 'Ruim — dificuldade significativa na maioria das vezes', 'Muito ruim — não consigo me concentrar'],
    },
    {
      pergunta: 'Nas últimas duas semanas, com que frequência você teve sintomas físicos relacionados ao estresse (dores de cabeça, tensão muscular, dores no peito, etc.)?',
      opcoes: ['Nenhum sintoma', 'Raramente — 1 a 2 vezes', 'Às vezes — algumas vezes por semana', 'Frequentemente — quase todos os dias', 'Sempre — diariamente e com intensidade'],
    },
  ];

  ngOnInit(): void {
    this.http.get<any[]>('http://localhost:8080/questionario').subscribe({
      next: (dados) => {
        console.log('Questionário carregado:', dados);
        this.cdRef.detectChanges();
      },
      error: (err) => console.error('Erro ao buscar questionário:', err),
    });
  }

  obterPerguntaAtual(): string {
    return this.dadosQuestionario[this.etapaAtual]?.pergunta || '';
  }

  obterOpcoesAtuais(): string[] {
    return this.dadosQuestionario[this.etapaAtual]?.opcoes || [];
  }

  proximaEtapa(): void {
    if (!this.respostas[this.etapaAtual]) {
      alert('Por favor, selecione uma opção antes de continuar.');
      return;
    }

    if (this.etapaAtual < this.etapas.length - 1) {
      this.etapaAtual++;
      return;
    }

    console.log('Respostas finais do questionário:', this.respostas);
    alert('Questionário concluído e enviado com sucesso!');
    void this.router.navigate(['/agradecimento']);
  }

  etapaAnterior(): void {
    if (this.etapaAtual > 0) {
      this.etapaAtual--;
    }
  }
}
