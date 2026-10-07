package br.com.psicossocial.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AplicacaoEdicaoDTO(
        @Future
        @NotNull
        LocalDateTime dataInicio,
        @Future
        @NotNull
        LocalDateTime dataTermino
){
}
