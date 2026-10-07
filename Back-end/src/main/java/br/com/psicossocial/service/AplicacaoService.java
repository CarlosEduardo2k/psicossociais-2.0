package br.com.psicossocial.service;

import br.com.psicossocial.dto.AplicacaoCadastroDTO;
import br.com.psicossocial.dto.AplicacaoEdicaoDTO;
import br.com.psicossocial.dto.AplicacaoResponseDTO;
import br.com.psicossocial.entity.Aplicacao;
import br.com.psicossocial.entity.Empresa;
import br.com.psicossocial.entity.Questionario;
import br.com.psicossocial.entity.StatusAplicacao;
import br.com.psicossocial.exception.RegraDeNegocioException;
import br.com.psicossocial.repository.AplicacaoRepository;
import br.com.psicossocial.repository.EmpresaRepository;
import br.com.psicossocial.repository.QuestionarioRepository;
import org.springframework.stereotype.Service;

/**
 * Implementa as regras de negócio relacionadas às aplicações de questionários.
 * <p>
 * O serviço valida o período informado, vincula empresa e questionário à nova
 * aplicação e permite alterações apenas enquanto ela estiver agendada.
 * </p>
 */
@Service
public class AplicacaoService {
    /** Repositório responsável pelo acesso às aplicações. */
    private final AplicacaoRepository  aplicacaoRepository;
    /** Repositório usado para localizar a empresa vinculada à aplicação. */
    private final EmpresaRepository empresaRepository;
    /** Repositório usado para localizar o questionário vinculado à aplicação. */
    private final QuestionarioRepository questionarioRepository;

    /**
     * Injeta os repositórios necessários para gerenciar aplicações.
     *
     * @param aplicacaoRepository repositório de aplicações
     * @param empresaRepository repositório de empresas
     * @param questionarioRepository repositório de questionários
     */
    public AplicacaoService (AplicacaoRepository aplicacaoRepository,
                             EmpresaRepository empresaRepository,
                             QuestionarioRepository questionarioRepository){
        this.aplicacaoRepository = aplicacaoRepository;
        this.empresaRepository = empresaRepository;
        this.questionarioRepository = questionarioRepository;
    }
    /**
     * Agenda uma nova aplicação de questionário para uma empresa.
     *
     * @param dadosCadastro período e identificadores da empresa e do questionário
     * @return dados da aplicação criada com status inicial agendado
     * @throws RegraDeNegocioException se o período for inválido ou os vínculos não existirem
     */
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
    /**
     * Busca uma aplicação pelo identificador.
     *
     * @param id identificador da aplicação
     * @return dados da aplicação encontrada
     * @throws RegraDeNegocioException se não existir aplicação com o ID informado
     */
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

    /**
     * Atualiza o período de uma aplicação agendada.
     *
     * @param id identificador da aplicação a ser alterada
     * @param dadosEdicao novas datas de início e término
     * @return dados atualizados da aplicação
     * @throws RegraDeNegocioException se a aplicação não existir, não estiver agendada ou o período for inválido
     */
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

    /**
     * Obtém a empresa vinculada à aplicação.
     *
     * @param id identificador da empresa
     * @return entidade encontrada
     * @throws RegraDeNegocioException se a empresa não for encontrada
     */
    private Empresa buscarEmpresaPorId(Integer id){
        return empresaRepository.findById(id).orElseThrow(()->
                new RegraDeNegocioException("Empresa não encontrada."));
    }
    /**
     * Obtém o questionário vinculado à aplicação.
     *
     * @param id identificador do questionário
     * @return entidade encontrada
     * @throws RegraDeNegocioException se o questionário não for encontrado
     */
    private Questionario buscarQuestionarioPorId(Integer id){
        return questionarioRepository.findById(id).orElseThrow(()->
                new RegraDeNegocioException("Questionário não encontrado."));
    }
    /**
     * Obtém a entidade de aplicação ou sinaliza sua ausência.
     *
     * @param id identificador da aplicação
     * @return entidade encontrada
     * @throws RegraDeNegocioException se a aplicação não for encontrada
     */
    private Aplicacao buscarAplicacaoPorId(Integer id){
        return aplicacaoRepository.findById(id).orElseThrow(()->
                new RegraDeNegocioException("Aplicação não encontrada."));
    }
}
