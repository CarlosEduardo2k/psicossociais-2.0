package br.com.psicossocial.dto;

public record AdministradorResponseDTO(
        Integer id,
        String nome,
        String cpf,
        String email
){
}
