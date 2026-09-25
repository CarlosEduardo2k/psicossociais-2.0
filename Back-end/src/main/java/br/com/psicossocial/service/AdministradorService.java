package br.com.psicossocial.service;

import br.com.psicossocial.dto.AdministradorCadastroDTO;
import br.com.psicossocial.dto.AdministradorEdicaoDTO;
import br.com.psicossocial.dto.AdministradorResponseDTO;
import br.com.psicossocial.entity.Administrador;
import br.com.psicossocial.exception.RegraDeNegocioException;
import br.com.psicossocial.repository.AdministradorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    public AdministradorService(
            AdministradorRepository administradorRepository,
            PasswordEncoder passwordEncoder) {
        this.administradorRepository = administradorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AdministradorResponseDTO cadastrar(AdministradorCadastroDTO dadosCadastro) {
        if (administradorRepository.existsByCpf(dadosCadastro.cpf())) {
            throw new RegraDeNegocioException("CPF já cadastrado.");
        }
        if (administradorRepository.existsByEmail(dadosCadastro.email())) {
            throw new RegraDeNegocioException("E-mail já cadastrado.");
        }
        Administrador novoAdministrador = new Administrador();
        novoAdministrador.setNome(dadosCadastro.nome());
        novoAdministrador.setEmail(dadosCadastro.email());
        novoAdministrador.setCpf(dadosCadastro.cpf());

        String senhaCriptografada = passwordEncoder.encode(dadosCadastro.senha());
        novoAdministrador.setSenha(senhaCriptografada);

        Administrador administradorSalvo = administradorRepository.save(novoAdministrador);
        return  new AdministradorResponseDTO(
                administradorSalvo.getId(),
                administradorSalvo.getNome(),
                administradorSalvo.getCpf(),
                administradorSalvo.getEmail()
        );
    }
    public AdministradorResponseDTO buscarPorId(Integer id) {
        Administrador administrador = buscarEntidadePorId(id);
        return new AdministradorResponseDTO(
                administrador.getId(),
                administrador.getNome(),
                administrador.getCpf(),
                administrador.getEmail());
    }
    public AdministradorResponseDTO atualizar(Integer id, AdministradorEdicaoDTO dadosEdicao) {

        Administrador administrador = buscarEntidadePorId(id);
        administrador.setNome(dadosEdicao.nome());
        administrador.setEmail(dadosEdicao.email());
        Administrador administradorSalvo = administradorRepository.save(administrador);

        return new AdministradorResponseDTO(
                administradorSalvo.getId(),
                administradorSalvo.getNome(),
                administradorSalvo.getCpf(),
                administradorSalvo.getEmail());
    }
    private Administrador buscarEntidadePorId(Integer id) {

        return administradorRepository.findById(id).orElseThrow(() -> new
                RegraDeNegocioException("Administrador não encontrado."));
    }
}