package br.com.psicossocial.service;

import br.com.psicossocial.dto.EmpresaCadastroDTO;
import br.com.psicossocial.dto.EmpresaEdicaoDTO;
import br.com.psicossocial.dto.EmpresaResponseDTO;
import br.com.psicossocial.entity.Administrador;
import br.com.psicossocial.entity.Empresa;
import br.com.psicossocial.exception.RegraDeNegocioException;
import br.com.psicossocial.repository.AdministradorRepository;
import br.com.psicossocial.repository.EmpresaRepository;
import org.springframework.stereotype.Service;


/**
 * Implementa as regras de negócio relacionadas às empresas.
 * <p>
 * O serviço valida a unicidade do CNPJ e associa cada empresa a um
 * administrador já cadastrado.
 * </p>
 */
@Service
public class EmpresaService {

        /** Repositório responsável pelo acesso às empresas. */
        private final EmpresaRepository empresaRepository;
        /** Repositório usado para localizar o administrador vinculado à empresa. */
        private final AdministradorRepository administradorRepository;

        /**
         * Injeta os repositórios necessários para gerenciar empresas.
         *
         * @param empresaRepository repositório de empresas
         * @param administradorRepository repositório de administradores
         */
        public EmpresaService(EmpresaRepository empresaRepository, AdministradorRepository administradorRepository ) {
            this.empresaRepository = empresaRepository;
            this.administradorRepository = administradorRepository;
        }

        /**
         * Cadastra uma empresa e a associa ao administrador informado.
         *
         * @param dadosCadastro dados da empresa e identificador do administrador
         * @return dados da empresa criada
         * @throws RegraDeNegocioException se o CNPJ já existir ou o administrador não for encontrado
         */
        public EmpresaResponseDTO cadastrar(EmpresaCadastroDTO dadosCadastro){
            if (empresaRepository.existsByCnpj(dadosCadastro.cnpj())){
                throw  new RegraDeNegocioException("CNPJ já cadastrado.");
            }
            Administrador administrador = buscarAdministradorPorId(dadosCadastro.administradorId());
            Empresa novaEmpresa = new Empresa();
            novaEmpresa.setNome(dadosCadastro.nome());
            novaEmpresa.setCnpj(dadosCadastro.cnpj());
            novaEmpresa.setAdministrador(administrador);

            Empresa empresaSalva = empresaRepository.save(novaEmpresa);
            return  new EmpresaResponseDTO(
                    empresaSalva.getId(),
                    empresaSalva.getNome(),
                    empresaSalva.getCnpj(),
                    empresaSalva.getAdministrador().getId());
        }
        /**
         * Busca uma empresa pelo identificador.
         *
         * @param id identificador da empresa
         * @return dados da empresa encontrada
         * @throws RegraDeNegocioException se não existir empresa com o ID informado
         */
        public EmpresaResponseDTO buscarPorId(Integer id){
            Empresa empresa = buscarEmpresaPorId(id);
            return new EmpresaResponseDTO(
                    empresa.getId(),
                    empresa.getNome(),
                    empresa.getCnpj(),
                    empresa.getAdministrador().getId());
        }
        /**
         * Atualiza o nome e o CNPJ de uma empresa existente.
         *
         * @param id identificador da empresa a ser alterada
         * @param dadosEdicao novos dados da empresa
         * @return dados atualizados da empresa
         * @throws RegraDeNegocioException se a empresa não existir ou o CNPJ pertencer a outra empresa
         */
        public EmpresaResponseDTO atualizar(Integer id, EmpresaEdicaoDTO dadosEdicao) {
            Empresa empresa = buscarEmpresaPorId(id);

            if (empresaRepository.existsByCnpjAndIdNot(dadosEdicao.cnpj(), id)) {
                throw new RegraDeNegocioException("CNPJ Já Cadastrado.");
            }
            empresa.setNome(dadosEdicao.nome());
            empresa.setCnpj(dadosEdicao.cnpj());
            Empresa empresaSalva = empresaRepository.save(empresa);
            return new EmpresaResponseDTO(
                    empresaSalva.getId(),
                    empresaSalva.getNome(),
                    empresaSalva.getCnpj(),
                    empresaSalva.getAdministrador().getId());
        }

        /**
         * Obtém a entidade de empresa ou sinaliza sua ausência.
         *
         * @param id identificador da empresa
         * @return entidade encontrada
         * @throws RegraDeNegocioException se a empresa não for encontrada
         */
        private Empresa buscarEmpresaPorId(Integer id){
            return empresaRepository.findById(id).orElseThrow(()->
                    new RegraDeNegocioException("Empresa não encontrada"));
        }

        /**
         * Obtém o administrador associado a uma empresa.
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

