
package br.com.psicossocial.dto;

import java.math.BigDecimal;

public record ResultadoResponseDTO(
        Integer id,
        BigDecimal mediaGeral,
        Integer participacaoId
) {
}
