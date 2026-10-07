package br.com.psicossocial.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDateTime;

public record AplicacaoCadastroDTO(

        @NotNull
        @Future
        LocalDateTime dataInicio,
        @NotNull
        @Future
        LocalDateTime dataTermino,
        @NotNull
        Integer empresaId,
        @NotNull
        Integer questionarioId
){
}
