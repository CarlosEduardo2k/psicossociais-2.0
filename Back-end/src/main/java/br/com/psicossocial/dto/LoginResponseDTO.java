package br.com.psicossocial.dto;

import br.com.psicossocial.enums.PerfilAdministrador;

public record LoginResponseDTO(
        Integer id,
        String nome,
        String cpf,
        String email,
        PerfilAdministrador perfil
) {
}
