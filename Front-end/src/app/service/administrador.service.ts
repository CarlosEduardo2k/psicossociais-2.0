import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, forkJoin, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { DashboardData } from '@models/dashboard.model';

export interface ResultadoResponseDTO {
  id: number;
  mediaGeral: number;
  participacaoId: number;
}

@Injectable({
  providedIn: 'root'
})
export class AdministradorService {
  private http = inject(HttpClient);

  // URL base do backend e URL específica de administradores
  private baseUrl = 'http://localhost:8080';
  private apiUrl = `${this.baseUrl}/administradores`;

  // Busca os dados do administrador pelo id
  buscarPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // Atualiza os dados do administrador
  atualizar(id: number, dadosEdicao: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, dadosEdicao);
  }

  // Busca resultados por participacaoId e monta o DashboardData no front-end
  carregarDadosDashboard(participacaoIds: number[]): Observable<DashboardData> {
    const requisicoes = participacaoIds.map((participacaoId) =>
      this.http
        .get<ResultadoResponseDTO>(`${this.baseUrl}/resultados/participacao/${participacaoId}`)
        .pipe(
          catchError(() => of(null))
        )
    );

    return forkJoin(requisicoes).pipe(
      map((resultadosBrutos) => {
        const validos = resultadosBrutos.filter(
          (item): item is ResultadoResponseDTO => item !== null
        );

        const totalRespostas = validos.length;

        const somaMedias = validos.reduce(
          (acumulador, item) => acumulador + Number(item.mediaGeral || 0),
          0
        );

        const mediaEstresse =
          totalRespostas > 0 ? Number((somaMedias / totalRespostas).toFixed(1)) : 0;

        const riscoCritico = validos.filter(
          (item) => Number(item.mediaGeral) >= 4.2
        ).length;

        const riscoAlto = validos.filter(
          (item) => Number(item.mediaGeral) >= 3.5 && Number(item.mediaGeral) < 4.2
        ).length;

        return {
          mesReferencia: 'Dados do Banco',
          totalRespostas,
          riscoCritico,
          riscoAlto,
          mediaEstresse,
          nivelRiscoPorSetor: {
            setores: [],
            valores: []
          },
          tendenciaUltimosMeses: {
            meses: [],
            estresse: [],
            exaustao: [],
            qualidadeSono: []
          }
        };
      })
    );
  }
}