import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of, catchError } from 'rxjs';
import {
  Bloco,
  Pergunta,
  RespostaCadastro,
  RespostaResponse,
  ParticipacaoResponse,
  ResultadoResponse
} from '@models/questionario.models';

@Injectable({
  providedIn: 'root'
})
export class QuestionarioService {
  private http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080';

  private blocosMock: Bloco[] = [
    { id: 1, nome: 'Estresse Ocupacional', ordem: 1 },
    { id: 2, nome: 'Qualidade do Sono', ordem: 2 },
    { id: 3, nome: 'Exaustão Emocional', ordem: 3 },
    { id: 4, nome: 'Capacidade de Concentração', ordem: 4 },
    { id: 5, nome: 'Sintomas Físicos', ordem: 5 }
  ];

  private perguntasMock: Pergunta[] = [
    {
      id: 1,
      texto: 'Com que frequência você se sente sobrecarregado(a) pelas demandas do seu trabalho?',
      ordem: 1,
      positivo: false,
      blocoId: 1,
      categoriaNome: 'Estresse Ocupacional'
    }
  ];

  iniciarParticipacao(participanteId: number, aplicacaoId: number): Observable<ParticipacaoResponse> {
    const params = new HttpParams()
      .set('participanteId', participanteId)
      .set('aplicacaoId', aplicacaoId);

    return this.http.post<ParticipacaoResponse>(
      `${this.baseUrl}/participacoes/iniciar`,
      null,
      { params }
    );
  }

  salvarResposta(participacaoId: number, payload: RespostaCadastro): Observable<RespostaResponse> {
    return this.http.post<RespostaResponse>(
      `${this.baseUrl}/participacoes/${participacaoId}/respostas`,
      payload
    );
  }

  buscarRespostas(participacaoId: number): Observable<RespostaResponse[]> {
    return this.http.get<RespostaResponse[]>(
      `${this.baseUrl}/participacoes/${participacaoId}/respostas`
    );
  }

  finalizarParticipacao(participacaoId: number): Observable<ParticipacaoResponse> {
    return this.http.put<ParticipacaoResponse>(
      `${this.baseUrl}/participacoes/${participacaoId}/finalizar`,
      {}
    );
  }

  buscarResultadoPorParticipacao(participacaoId: number): Observable<ResultadoResponse> {
    return this.http.get<ResultadoResponse>(
      `${this.baseUrl}/resultados/participacao/${participacaoId}`
    );
  }

  listarBlocos(): Observable<Bloco[]> {
    return this.http.get<Bloco[]>(`${this.baseUrl}/bloco`).pipe(
      catchError(() => of(this.blocosMock))
    );
  }

  listarPerguntasPorBloco(blocoId: number): Observable<Pergunta[]> {
    return this.http.get<Pergunta[]>(`${this.baseUrl}/pergunta/${blocoId}`).pipe(
      catchError(() => of(this.perguntasMock.filter(p => p.blocoId === blocoId)))
    );
  }
}