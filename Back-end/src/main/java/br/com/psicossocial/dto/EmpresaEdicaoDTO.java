package br.com.psicossocial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmpresaEdicaoDTO(
        @NotBlank
        @Size(max = 150)
        String nome,

        @NotBlank
        @Size(min = 14, max = 14)
        String cnpj
) {
}
