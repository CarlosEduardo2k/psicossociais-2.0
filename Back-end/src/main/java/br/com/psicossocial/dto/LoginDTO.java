package br.com.psicossocial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record LoginDTO(
        @NotBlank
        @Size(max = 150)
        String nome,

        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String cpf,

        @NotBlank
        String senha
){
}
