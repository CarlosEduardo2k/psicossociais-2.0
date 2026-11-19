package br.com.psicossocial.service;

import br.com.psicossocial.dto.LoginDTO;
import br.com.psicossocial.dto.LoginResponseDTO;
import br.com.psicossocial.entity.Administrador;
import br.com.psicossocial.exception.RegraDeNegocioException;
import br.com.psicossocial.repository.AdministradorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {
    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoService(AdministradorRepository administradorRepository, PasswordEncoder passwordEncoder) {
        this.administradorRepository = administradorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO validarLogin (LoginDTO dadosLogin){
        Administrador administrador = administradorRepository.findByCpf(dadosLogin.cpf()).orElseThrow(()->
                new RegraDeNegocioException("Nome, CPF ou senha inválidos."));

        boolean nomeValido = administrador.getNome().equalsIgnoreCase(dadosLogin.nome().trim());
        boolean senhaValida = passwordEncoder.matches(dadosLogin.senha(), administrador.getSenha());

        if (!nomeValido || !senhaValida){
            throw new RegraDeNegocioException("Nome, CPF ou senha inválidos.");
        }
        return new LoginResponseDTO(
                administrador.getId(),
                administrador.getNome(),
                administrador.getCpf(),
                administrador.getEmail(),
                administrador.getPerfil());
    }
}
