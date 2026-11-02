export interface DashboardData {
  mesReferencia: string;
  totalRespostas: number;
  riscoCritico: number;
  riscoAlto: number;
  mediaEstresse: number;
  nivelRiscoPorSetor: {
    setores: string[];
    valores: number[];
  };
  tendenciaUltimosMeses: {
    meses: string[];
    estresse: number[];
    exaustao: number[];
    qualidadeSono: number[];
  };
}