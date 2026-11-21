import { ChangeDetectorRef, Component, inject, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { forkJoin } from 'rxjs';
import { QuestionarioService } from '@service/questionario.service';
import { Bloco, Pergunta } from '@models/questionario.models';

export interface OpcaoEscala {
  label: string;
  valor: number;
}

@Component({
  selector: 'app-questionario',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './questionario.html',
  styleUrls: ['./questionario.css'],
})
export class Questionario implements OnInit {
  @Input() nomeAdmin: string = 'Funcionario teste';
  @Input() emailAdmin: string = 'funcionario@teste.com';
  @Output() logoutEvent = new EventEmitter<void>();
  private readonly questionarioService = inject(QuestionarioService);
  private readonly router = inject(Router);
  private readonly cdRef = inject(ChangeDetectorRef);

  blocos: Bloco[] = [];
  blocoAtualIndex = 0;

  perguntasDoBloco: Pergunta[] = [];
  perguntaAtualIndex = 0;

  perguntasPorBlocoId: Record<number, Pergunta[]> = {};

  respostasPorPergunta: Record<number, number> = {};

  opcoesEscala: OpcaoEscala[] = [
    { label: 'Nunca ou raramente', valor: 1 },
    { label: 'Algumas vezes por mês', valor: 2 },
    { label: 'Algumas vezes por semana', valor: 3 },
    { label: 'Quase todos os dias', valor: 4 },
    { label: 'Todos os dias, o tempo todo', valor: 5 },
  ];

  ngOnInit(): void {
    this.carregarBlocos();
  }

  carregarBlocos(): void {
    this.questionarioService.listarBlocos().subscribe({
      next: (lista) => {
        this.blocos = [...lista].sort((a, b) => a.ordem - b.ordem);
        this.blocoAtualIndex = 0;

        if (this.blocos.length > 0) {
          this.carregarTodasAsPerguntas(this.blocos);
        }
        this.cdRef.detectChanges();
      },
      error: (err) => console.error('Erro ao carregar blocos:', err),
    });
  }

  private carregarTodasAsPerguntas(blocos: Bloco[]): void {
    const requisicoes = blocos.map((bloco) =>
      this.questionarioService.listarPerguntasPorBloco(bloco.id)
    );

    forkJoin(requisicoes).subscribe({
      next: (listasPorBloco) => {
        blocos.forEach((bloco, idx) => {
          this.perguntasPorBlocoId[bloco.id] = [...(listasPorBloco[idx] || [])].sort(
            (a, b) => a.ordem - b.ordem
          );
        });

        const primeiroBlocoId = blocos[0].id;
        this.perguntasDoBloco = this.perguntasPorBlocoId[primeiroBlocoId] || [];
        this.perguntaAtualIndex = 0;
        this.cdRef.detectChanges();
      },
      error: (err) => console.error('Erro ao carregar perguntas dos blocos:', err),
    });
  }

  selecionarBloco(index: number): void {
    if (index < 0 || index >= this.blocos.length) return;
    this.blocoAtualIndex = index;
    this.perguntaAtualIndex = 0;
    const blocoSelecionado = this.blocos[index];

    if (this.perguntasPorBlocoId[blocoSelecionado.id]) {
      this.perguntasDoBloco = this.perguntasPorBlocoId[blocoSelecionado.id];
      this.cdRef.detectChanges();
    } else {
      this.carregarPerguntasDoBloco(blocoSelecionado.id);
    }
  }

  private carregarPerguntasDoBloco(blocoId: number): void {
    this.questionarioService.listarPerguntasPorBloco(blocoId).subscribe({
      next: (lista) => {
        const ordenadas = [...lista].sort((a, b) => a.ordem - b.ordem);
        this.perguntasPorBlocoId[blocoId] = ordenadas;
        this.perguntasDoBloco = ordenadas;
        this.perguntaAtualIndex = 0;
        this.cdRef.detectChanges();
      },
      error: (err) => console.error('Erro ao carregar perguntas do bloco:', err),
    });
  }

  get blocoAtual(): Bloco | undefined {
    return this.blocos[this.blocoAtualIndex];
  }

  get perguntaAtual(): Pergunta | undefined {
    return this.perguntasDoBloco[this.perguntaAtualIndex];
  }

  get totalPerguntasBloco(): number {
    return this.perguntasDoBloco.length;
  }

  get totalPerguntasCarregadas(): number {
    return Object.values(this.perguntasPorBlocoId).reduce(
      (acc, lista) => acc + lista.length,
      0
    );
  }

  get totalRespondidas(): number {
    return Object.keys(this.respostasPorPergunta).length;
  }

  get percentualRespondidoGeral(): number {
    if (this.totalPerguntasCarregadas === 0) return 0;
    return (this.totalRespondidas / this.totalPerguntasCarregadas) * 100;
  }

  get percentualProgressoBloco(): number {
    if (this.totalPerguntasBloco === 0) return 0;
    return ((this.perguntaAtualIndex + 1) / this.totalPerguntasBloco) * 100;
  }

  get isUltimaPerguntaDoUltimoBloco(): boolean {
    const ultimoBloco = this.blocoAtualIndex === this.blocos.length - 1;
    const ultimaPergunta = this.perguntaAtualIndex === this.totalPerguntasBloco - 1;
    return ultimoBloco && ultimaPergunta;
  }

  get respostaAtualValor(): number | undefined {
    const atual = this.perguntaAtual;
    if (!atual) return undefined;
    return this.respostasPorPergunta[atual.id];
  }

  set respostaAtualValor(valor: number | undefined) {
    const atual = this.perguntaAtual;
    if (!atual || valor === undefined) return;
    this.respostasPorPergunta[atual.id] = valor;
  }

  proximaEtapa(): void {
    const atual = this.perguntaAtual;
    if (!atual) return;

    if (this.respostasPorPergunta[atual.id] === undefined) {
      alert('Por favor, selecione uma opção antes de continuar.');
      return;
    }

    // Avança para a próxima pergunta do bloco atual
    if (this.perguntaAtualIndex < this.totalPerguntasBloco - 1) {
      this.perguntaAtualIndex++;
      return;
    }

    // Terminou o bloco atual: vai para o próximo bloco
    if (this.blocoAtualIndex < this.blocos.length - 1) {
      this.selecionarBloco(this.blocoAtualIndex + 1);
      return;
    }

    // Última pergunta do último bloco
    console.log('Respostas coletadas (perguntaId -> valor):', this.respostasPorPergunta);
    alert('Questionário concluído!');
    void this.router.navigate(['/agradecimento']);
  }

  etapaAnterior(): void {
    if (this.perguntaAtualIndex > 0) {
      this.perguntaAtualIndex--;
      return;
    }

    if (this.blocoAtualIndex > 0) {
      const indiceAnterior = this.blocoAtualIndex - 1;
      this.blocoAtualIndex = indiceAnterior;
      const blocoAnterior = this.blocos[indiceAnterior];
      const listaAnterior = this.perguntasPorBlocoId[blocoAnterior.id] || [];
      this.perguntasDoBloco = listaAnterior;
      this.perguntaAtualIndex = Math.max(0, listaAnterior.length - 1);
      this.cdRef.detectChanges();
    }
  }

    // aqui ele pega as iniciais do usuário e cria o avatar
  get iniciaisAvatar(): string {
    if (!this.nomeAdmin) return 'AD';
    const partes = this.nomeAdmin.trim().split(' ');
    if (partes.length >= 2) {
      return (partes[0][0] + partes[1][0]).toUpperCase();
    }
    return this.nomeAdmin.substring(0, 2).toUpperCase();
  }

  onLogout(): void {
    this.logoutEvent.emit();
    void this.router.navigate(['/']);
  }
}