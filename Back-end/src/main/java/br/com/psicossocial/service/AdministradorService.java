
package br.com.psicossocial.service;

import br.com.psicossocial.dto.AdministradorCadastroDTO;
import br.com.psicossocial.dto.AdministradorEdicaoDTO;
import br.com.psicossocial.dto.AdministradorResponseDTO;
import br.com.psicossocial.entity.Administrador;
import br.com.psicossocial.enums.PerfilAdministrador;
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
        novoAdministrador.setCpf(dadosCadastro.cpf());
        novoAdministrador.setEmail(dadosCadastro.email());

        String senhaCriptografada = passwordEncoder.encode(dadosCadastro.senha());
        novoAdministrador.setSenha(senhaCriptografada);
        // O cadastro comum sempre cria um ADMINISTRADOR.
        novoAdministrador.setPerfil(PerfilAdministrador.ADMINISTRADOR);
        Administrador administradorSalvo = administradorRepository.save(novoAdministrador);
        return converterParaResponseDTO(administradorSalvo);
    }

    public AdministradorResponseDTO buscarPorId(Integer id) {
        Administrador administrador = buscarAdministradorPorId(id);
        return converterParaResponseDTO(administrador);
    }

    public AdministradorResponseDTO atualizarAdministrador(Integer id,AdministradorEdicaoDTO dadosEdicao){
        Administrador administrador = buscarAdministradorPorId(id);
        boolean emailAlterado = !administrador.getEmail().equalsIgnoreCase(dadosEdicao.email());
        if (emailAlterado && administradorRepository.existsByEmailAndIdNot(dadosEdicao.email(), id))
        {throw new RegraDeNegocioException("E-mail já cadastrado.");}
        administrador.setNome(dadosEdicao.nome());
        administrador.setEmail(dadosEdicao.email());
        Administrador administradorSalvo = administradorRepository.save(administrador);
        return converterParaResponseDTO(administradorSalvo);
    }


    //métodos privados da classe
    private Administrador buscarAdministradorPorId(Integer id) {
        return administradorRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Administrador não encontrado."));
    }
    private AdministradorResponseDTO converterParaResponseDTO(Administrador administrador) {
        return new AdministradorResponseDTO(
                administrador.getId(),
                administrador.getNome(),
                administrador.getCpf(),
                administrador.getEmail(),
                administrador.getPerfil());
    }
}
