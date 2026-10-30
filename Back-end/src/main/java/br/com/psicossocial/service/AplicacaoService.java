package br.com.psicossocial.service;

import br.com.psicossocial.dto.AplicacaoCadastroDTO;
import br.com.psicossocial.dto.AplicacaoEdicaoDTO;
import br.com.psicossocial.dto.AplicacaoResponseDTO;
import br.com.psicossocial.entity.Aplicacao;
import br.com.psicossocial.entity.Empresa;
import br.com.psicossocial.entity.Questionario;
import br.com.psicossocial.enums.StatusAplicacao;
import br.com.psicossocial.exception.RegraDeNegocioException;
import br.com.psicossocial.repository.AplicacaoRepository;
import br.com.psicossocial.repository.EmpresaRepository;
import br.com.psicossocial.repository.QuestionarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementa as regras de negócio relacionadas às aplicações de questionários.
 * <p>
 * O serviço valida o período informado, vincula empresa e questionário à nova
 * aplicação e permite alterações apenas enquanto ela estiver agendada.
 * </p>
 */
@Service
public class AplicacaoService {
    private final AplicacaoRepository  aplicacaoRepository;
    private final EmpresaRepository empresaRepository;
    private final QuestionarioRepository questionarioRepository;

    public AplicacaoService (AplicacaoRepository aplicacaoRepository,
                             EmpresaRepository empresaRepository,
                             QuestionarioRepository questionarioRepository){
        this.aplicacaoRepository = aplicacaoRepository;
        this.empresaRepository = empresaRepository;
        this.questionarioRepository = questionarioRepository;
    }

    public AplicacaoResponseDTO cadastrar(AplicacaoCadastroDTO dadosCadastro){
        if (!dadosCadastro.dataInicio().isBefore(dadosCadastro.dataTermino())) {
            throw new RegraDeNegocioException("A data de início deve ser anterior à data de término.");
        }
        Empresa empresa = buscarEmpresaPorId(dadosCadastro.empresaId());
        Questionario questionario = buscarQuestionarioPorId(dadosCadastro.questionarioId());

        Aplicacao novaAplicacao = new Aplicacao();
        novaAplicacao.setDataInicio(dadosCadastro.dataInicio());
        novaAplicacao.setDataTermino(dadosCadastro.dataTermino());
        novaAplicacao.setStatus(StatusAplicacao.AGENDADA);
        novaAplicacao.setEmpresa(empresa);
        novaAplicacao.setQuestionario(questionario);
        Aplicacao aplicacaoSalva = aplicacaoRepository.save(novaAplicacao);

        return new  AplicacaoResponseDTO(
                aplicacaoSalva.getId(),
                aplicacaoSalva.getDataInicio(),
                aplicacaoSalva.getDataTermino(),
                aplicacaoSalva.getStatus(),
                aplicacaoSalva.getEmpresa().getId(),
                aplicacaoSalva.getQuestionario().getId());
    }
    public AplicacaoResponseDTO buscarPorId(Integer id){
            Aplicacao aplicacao = buscarAplicacaoPorId(id);
            return new AplicacaoResponseDTO(
                    aplicacao.getId(),
                    aplicacao.getDataInicio(),
                    aplicacao.getDataTermino(),
                    aplicacao.getStatus(),
                    aplicacao.getEmpresa().getId(),
                    aplicacao.getQuestionario().getId());
    }
    public AplicacaoResponseDTO atualizar(Integer id ,AplicacaoEdicaoDTO dadosEdicao){
        Aplicacao aplicacao = buscarAplicacaoPorId(id);
        if (aplicacao.getStatus() != StatusAplicacao.AGENDADA) {
            throw new RegraDeNegocioException("Somente aplicações agendadas podem ser alteradas.");
        }
        if (!dadosEdicao.dataInicio().isBefore(dadosEdicao.dataTermino())) {
            throw new RegraDeNegocioException("A data de início deve ser anterior à data de término.");
        }

        aplicacao.setDataInicio(dadosEdicao.dataInicio());
        aplicacao.setDataTermino(dadosEdicao.dataTermino());
        Aplicacao aplicacaoSalva = aplicacaoRepository.save(aplicacao);

        return new AplicacaoResponseDTO(
                aplicacaoSalva.getId(),
                aplicacaoSalva.getDataInicio(),
                aplicacaoSalva.getDataTermino(),
                aplicacaoSalva.getStatus(),
                aplicacaoSalva.getEmpresa().getId(),
                aplicacaoSalva.getQuestionario().getId());
    }

    // métodos de abertura e fechamento de uma aplicação

    public AplicacaoResponseDTO abrir(Integer id) {
        Aplicacao aplicacao = buscarAplicacaoPorId(id);
        if (aplicacao.getStatus() != StatusAplicacao.AGENDADA) {
            throw new RegraDeNegocioException("Somente aplicações agendadas podem ser abertas.");
        }
        aplicacao.setStatus(StatusAplicacao.ABERTA);
        Aplicacao aplicacaoSalva = aplicacaoRepository.save(aplicacao);
        return new AplicacaoResponseDTO(
                aplicacaoSalva.getId(),
                aplicacaoSalva.getDataInicio(),
                aplicacaoSalva.getDataTermino(),
                aplicacaoSalva.getStatus(),
                aplicacaoSalva.getEmpresa().getId(),
                aplicacaoSalva.getQuestionario().getId());
    }

    public AplicacaoResponseDTO fechar(Integer id) {
        Aplicacao aplicacao = buscarAplicacaoPorId(id);
        if (aplicacao.getStatus() != StatusAplicacao.ABERTA) {
            throw new RegraDeNegocioException("Somente aplicações abertas podem ser fechadas.");
        }
        aplicacao.setStatus(StatusAplicacao.FECHADA);
        Aplicacao aplicacaoSalva = aplicacaoRepository.save(aplicacao);
        return new AplicacaoResponseDTO(
                aplicacaoSalva.getId(),
                aplicacaoSalva.getDataInicio(),
                aplicacaoSalva.getDataTermino(),
                aplicacaoSalva.getStatus(),
                aplicacaoSalva.getEmpresa().getId(),
                aplicacaoSalva.getQuestionario().getId());
    }

    public void fecharAplicacaoAutomaticamente(){
        List<Aplicacao> aplicacaoExpirada = aplicacaoRepository.findByStatusAndDataTerminoLessThanEqual(
                StatusAplicacao.ABERTA, LocalDateTime.now());

        for (Aplicacao aplicacao : aplicacaoExpirada) {
            aplicacao.setStatus(StatusAplicacao.FECHADA);
        }
        aplicacaoRepository.saveAll(aplicacaoExpirada);
    }


    // métodos privados para busca dentro da classe
    private Empresa buscarEmpresaPorId(Integer id){
        return empresaRepository.findById(id).orElseThrow(()->
                new RegraDeNegocioException("Empresa não encontrada."));
    }
    private Questionario buscarQuestionarioPorId(Integer id){
        return questionarioRepository.findById(id).orElseThrow(()->
                new RegraDeNegocioException("Questionário não encontrado."));
    }
    private Aplicacao buscarAplicacaoPorId(Integer id){
        return aplicacaoRepository.findById(id).orElseThrow(()->
                new RegraDeNegocioException("Aplicação não encontrada."));
    }
}
