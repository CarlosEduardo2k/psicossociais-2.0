export interface Bloco {
  id: number;
  nome: string;
  ordem: number;
  questionarioId?: number;
}

export interface Categoria {
  id: number;
  nome: string;
}

export interface Pergunta {
  id: number;
  texto: string;
  ordem: number;
  positivo: boolean;
  blocoId: number;
  categoria?: Categoria;
  categoriaNome?: string;
}

export interface RespostaCadastro {
  perguntaId: number;
  valor: number;
}

export interface RespostaResponse {
  id: number;
  valor: number;
  perguntaId: number;
  participacaoId: number;
}

export interface ParticipacaoResponse {
  id: number;
  dataInicio: string;
  dataTermino: string | null;
  status: string;
  participanteId: number;
  aplicacaoId: number;
}

export interface ResultadoResponse {
  id: number;
  mediaGeral: number;
  participacaoId: number;
}