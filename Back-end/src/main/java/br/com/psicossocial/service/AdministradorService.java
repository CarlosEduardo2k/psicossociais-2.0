package br.com.psicossocial.service;

import br.com.psicossocial.dto.AdministradorCadastroDTO;
import br.com.psicossocial.dto.AdministradorEdicaoDTO;
import br.com.psicossocial.dto.AdministradorResponseDTO;
import br.com.psicossocial.entity.Administrador;
import br.com.psicossocial.exception.RegraDeNegocioException;
import br.com.psicossocial.repository.AdministradorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementa as regras de negócio relacionadas aos administradores.
 * <p>
 * Além de persistir os dados, o serviço garante a unicidade de CPF e e-mail
 * e armazena a senha de forma codificada antes do cadastro.
 * </p>
 */
@Service
public class AdministradorService {

    /** Repositório responsável pelo acesso aos administradores. */
    private final AdministradorRepository administradorRepository;
    /** Componente usado para codificar senhas. */
    private final PasswordEncoder passwordEncoder;

    /**
     * Injeta as dependências usadas pelas operações de administrador.
     *
     * @param administradorRepository repositório de administradores
     * @param passwordEncoder codificador de senhas
     */
    public AdministradorService(
            AdministradorRepository administradorRepository,
            PasswordEncoder passwordEncoder) {
        this.administradorRepository = administradorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Cadastra um administrador após verificar CPF e e-mail ainda não utilizados.
     *
     * @param dadosCadastro dados do novo administrador, incluindo a senha em texto
     * @return dados do administrador criado, sem expor a senha
     * @throws RegraDeNegocioException se o CPF ou o e-mail já estiver cadastrado
     */
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
    /**
     * Busca um administrador pelo identificador.
     *
     * @param id identificador do administrador
     * @return dados do administrador encontrado
     * @throws RegraDeNegocioException se não existir administrador com o ID informado
     */
    public AdministradorResponseDTO buscarPorId(Integer id) {
        Administrador administrador = buscarAdministradorPorId(id);
        return new AdministradorResponseDTO(
                administrador.getId(),
                administrador.getNome(),
                administrador.getCpf(),
                administrador.getEmail());
    }
    /**
     * Atualiza o nome e o e-mail de um administrador existente.
     *
     * @param id identificador do administrador a ser alterado
     * @param dadosEdicao novos dados do administrador
     * @return dados atualizados do administrador
     * @throws RegraDeNegocioException se não existir administrador com o ID informado
     */
    public AdministradorResponseDTO atualizarAdministrador(Integer id, AdministradorEdicaoDTO dadosEdicao) {

        Administrador administrador = buscarAdministradorPorId(id);
        administrador.setNome(dadosEdicao.nome());
        administrador.setEmail(dadosEdicao.email());
        Administrador administradorSalvo = administradorRepository.save(administrador);

        return new AdministradorResponseDTO(
                administradorSalvo.getId(),
                administradorSalvo.getNome(),
                administradorSalvo.getCpf(),
                administradorSalvo.getEmail());
    }
    /**
     * Obtém a entidade de administrador ou sinaliza sua ausência.
     *
     * @param id identificador do administrador
     * @return entidade encontrada
     * @throws RegraDeNegocioException se o administrador não for encontrado
     */
    private Administrador buscarAdministradorPorId(Integer id) {

        return administradorRepository.findById(id).orElseThrow(() -> new
                RegraDeNegocioException("Administrador não encontrado."));
    }
}
