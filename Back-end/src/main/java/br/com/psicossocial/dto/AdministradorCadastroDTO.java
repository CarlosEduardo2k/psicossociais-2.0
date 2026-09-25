package br.com.psicossocial.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdministradorCadastroDTO(
        @NotBlank
        @Size(max = 150)
        String nome,

        @NotBlank
        @Size(min = 11, max = 11)
        String cpf,

        @NotBlank
        @Email
        @Size(max = 150)
        String email,

        @NotBlank
        String senha){
}
