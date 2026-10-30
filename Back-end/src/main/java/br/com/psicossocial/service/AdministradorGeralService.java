package br.com.psicossocial.service;

import br.com.psicossocial.dto.AdministradorCadastroDTO;
import br.com.psicossocial.dto.AdministradorEdicaoDTO;
import br.com.psicossocial.dto.AdministradorResponseDTO;
import br.com.psicossocial.entity.Administrador;
import br.com.psicossocial.enums.PerfilAdministrador;
import br.com.psicossocial.exception.RegraDeNegocioException;
import br.com.psicossocial.repository.AdministradorRepository;
import br.com.psicossocial.repository.AplicacaoRepository;
import br.com.psicossocial.repository.EmpresaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdministradorGeralService {
    private final EmpresaRepository empresaRepository;
    private final AplicacaoRepository aplicacaoRepository;
    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    public AdministradorGeralService(AdministradorRepository administradorRepository,
           PasswordEncoder passwordEncoder, EmpresaRepository empresaRepository,
           AplicacaoRepository aplicacaoRepository) {
        this.administradorRepository = administradorRepository;
        this.passwordEncoder = passwordEncoder;
        this.empresaRepository = empresaRepository;
        this.aplicacaoRepository = aplicacaoRepository;

    }

    public AdministradorResponseDTO cadastrar(AdministradorCadastroDTO dadosCadastro){
        if (administradorRepository.existsByCpf(dadosCadastro.cpf())){
            throw new RegraDeNegocioException("CPF já cadastrado");
        }
        if (administradorRepository.existsByEmail(dadosCadastro.email())){
            throw new RegraDeNegocioException("E-mail já cadastrado");
        }
        Administrador novoAdministrador = new Administrador();
        novoAdministrador.setNome(dadosCadastro.nome());
        novoAdministrador.setCpf(dadosCadastro.cpf());
        novoAdministrador.setEmail(dadosCadastro.email());

        String senhaCriptografada = passwordEncoder.encode(dadosCadastro.senha());
        novoAdministrador.setSenha(senhaCriptografada);

        novoAdministrador.setPerfil(PerfilAdministrador.ADMINISTRADOR);
        Administrador administradorSalvo = administradorRepository.save(novoAdministrador);
        return converterParaResponseDTO(administradorSalvo);
    }

    public AdministradorResponseDTO buscarPorId(Integer id){
        Administrador administrador = buscarAdministradorPorId(id);
        return  converterParaResponseDTO(administrador);
    }

     public AdministradorResponseDTO atualizarAdministrador( Integer id,AdministradorEdicaoDTO dadosEdicao){
        Administrador administrador = buscarAdministradorPorId(id);

        boolean emailAlterado =! administrador.getEmail().equalsIgnoreCase(dadosEdicao.email());
        if (emailAlterado && administradorRepository.existsByEmailAndIdNot(dadosEdicao.email(), id)){
            throw new RegraDeNegocioException("E-mail já Cadastrado");
        }
        administrador.setNome(dadosEdicao.nome());
        administrador.setEmail(dadosEdicao.email());
        Administrador administradorSalvo = administradorRepository.save(administrador);
        return converterParaResponseDTO(administradorSalvo);
    }

    public void excluir( Integer id, Integer idSolicitante){

        Administrador administrador = buscarAdministradorPorId(id);

        if (id.equals(idSolicitante)){
            throw new RegraDeNegocioException("Não é permitido Excluir a propria conta");
        }
        if (administrador.getPerfil() == PerfilAdministrador.ADMIN_GERAL){
            long totalAdministradoresGerais = administradorRepository.countByPerfil(PerfilAdministrador.ADMIN_GERAL);
        if (totalAdministradoresGerais <= 1){
            throw new RegraDeNegocioException("Não é possível excluir o ultimo administrador");
        }
        }

        boolean possuiEmpresas = empresaRepository.existsByAdministrador_Id(id);
        boolean possuiAplicacao = aplicacaoRepository.existisByEmpresa_Administrador_Id(id);
        if (possuiAplicacao || possuiEmpresas){
            throw new RegraDeNegocioException("Não é possível Excluir o administrador, pois existe empresas ou aplicação vinculadas");
        }
        administradorRepository.delete(administrador);
    }

    //Métodos privados da classe
    private AdministradorResponseDTO converterParaResponseDTO(Administrador administrador){
        return new AdministradorResponseDTO(
                administrador.getId(),
                administrador.getNome(),
                administrador.getCpf(),
                administrador.getEmail(),
                administrador.getPerfil());
    }
    private Administrador buscarAdministradorPorId(Integer id){
        return administradorRepository.findById(id).orElseThrow(()->
                new RegraDeNegocioException("Administrador não encontrado"));
    }
}