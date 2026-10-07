package br.com.psicossocial.dto;

import br.com.psicossocial.entity.StatusAplicacao;
import java.time.LocalDateTime;

public record AplicacaoResponseDTO(
        Integer id,
        LocalDateTime dataInicio,
        LocalDateTime dataTermino,
        StatusAplicacao status,
        Integer empresaId,
        Integer questionarioId
) {
}
