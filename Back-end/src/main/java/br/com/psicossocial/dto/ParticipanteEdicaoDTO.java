
package br.com.psicossocial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ParticipanteEdicaoDTO(

        @NotBlank(message = "O CPF é obrigatório.")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 números.")
        String cpf,

        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
        String nome,

        @NotBlank(message = "O cargo é obrigatório.")
        @Size(max = 100, message = "O cargo deve ter no máximo 100 caracteres.")
        String cargo,

        @NotBlank(message = "O setor é obrigatório.")
        @Size(max = 100, message = "O setor deve ter no máximo 100 caracteres.")
        String setor

) {
}
