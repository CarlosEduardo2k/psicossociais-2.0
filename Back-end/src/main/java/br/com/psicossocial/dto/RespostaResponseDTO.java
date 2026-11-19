
package br.com.psicossocial.dto;

public record RespostaResponseDTO(
        Integer id,
        Integer valor,
        Integer perguntaId,
        Integer participacaoId
) {
}
