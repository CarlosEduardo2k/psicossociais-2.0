
package br.com.psicossocial.dto;

public record ParticipanteResponseDTO(
        Integer id,
        String cpf,
        String nome,
        String cargo,
        String setor
) {
}
