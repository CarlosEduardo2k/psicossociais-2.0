
package br.com.psicossocial.dto;

import br.com.psicossocial.entity.StatusParticipacao;
import java.time.LocalDateTime;

public record ParticipacaoResponseDTO(
        Integer id,
        LocalDateTime dataInicio,
        LocalDateTime dataTermino,
        StatusParticipacao status,
        Integer participanteId,
        Integer aplicacaoId
) {
}
