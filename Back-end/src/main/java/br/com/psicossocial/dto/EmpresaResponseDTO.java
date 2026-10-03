package br.com.psicossocial.dto;

public record EmpresaResponseDTO(
        Integer id,
        String nome,
        String cnpj,
        Integer administradorId
) {
}
