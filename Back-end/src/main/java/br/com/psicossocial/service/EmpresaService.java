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


@Service
public class EmpresaService {

        private final EmpresaRepository empresaRepository;
        private final AdministradorRepository administradorRepository;

        public EmpresaService(EmpresaRepository empresaRepository,  AdministradorRepository administradorRepository ) {
            this.empresaRepository = empresaRepository;
            this.administradorRepository = administradorRepository;
        }

        public EmpresaResponseDTO cadastrar(EmpresaCadastroDTO dadosCadastro){
            if (empresaRepository.existsByCnpj(dadosCadastro.cnpj())){
                throw  new RegraDeNegocioException("CNPJ já cadastrado");
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
        public EmpresaResponseDTO buscarPorId(Integer id){
            Empresa empresa = buscarEmpresaPorId(id);
            return new EmpresaResponseDTO(
                    empresa.getId(),
                    empresa.getNome(),
                    empresa.getCnpj(),
                    empresa.getAdministrador().getId());
        }
        public EmpresaResponseDTO atualizarEmpresa(Integer id, EmpresaEdicaoDTO dadosEdicao) {
            Empresa empresa = buscarEmpresaPorId(id);

            empresa.setNome(dadosEdicao.nome());
            empresa.setCnpj(dadosEdicao.cnpj());
            Empresa empresaSalva = empresaRepository.save(empresa);
            return new EmpresaResponseDTO(
                    empresaSalva.getId(),
                    empresaSalva.getNome(),
                    empresaSalva.getCnpj(),
                    empresaSalva.getAdministrador().getId());
        }

        private Empresa buscarEmpresaPorId(Integer id){
            return empresaRepository.findById(id).orElseThrow(()->
                    new RegraDeNegocioException("empresa não encontrada"));
        }

        private Administrador buscarAdministradorPorId(Integer id) {
            return administradorRepository.findById(id).orElseThrow(() -> new
                    RegraDeNegocioException("Administrador não encontrado."));
        }
}

