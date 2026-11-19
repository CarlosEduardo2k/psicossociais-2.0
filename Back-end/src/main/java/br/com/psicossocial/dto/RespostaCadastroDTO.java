
package br.com.psicossocial.dto;

import jakarta.validation.constraints.NotNull;

public record RespostaCadastroDTO(

        @NotNull(message = "O ID da pergunta é obrigatório.")
        Integer perguntaId,

        @NotNull(message = "O valor da resposta é obrigatório.")
        Integer valor

) {
}
