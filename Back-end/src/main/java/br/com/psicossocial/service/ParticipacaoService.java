
package br.com.psicossocial.service;

// DTOs utilizados pelo serviço.
import br.com.psicossocial.dto.ParticipacaoResponseDTO;
import br.com.psicossocial.dto.RespostaCadastroDTO;
import br.com.psicossocial.dto.RespostaResponseDTO;

// Entidades utilizadas pelo serviço.
import br.com.psicossocial.entity.Aplicacao;
import br.com.psicossocial.entity.Participacao;
import br.com.psicossocial.entity.Participante;
import br.com.psicossocial.entity.Pergunta;
import br.com.psicossocial.entity.Resposta;
import br.com.psicossocial.entity.StatusParticipacao;

// Repositórios responsáveis pelo acesso ao banco.
import br.com.psicossocial.repository.AplicacaoRepository;
import br.com.psicossocial.repository.ParticipacaoRepository;
import br.com.psicossocial.repository.ParticipanteRepository;
import br.com.psicossocial.repository.PerguntaRepository;
import br.com.psicossocial.repository.RespostaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// Indica que esta classe contém as regras de negócio
// relacionadas às participações nos questionários.
@Service
public class ParticipacaoService {

    // Repositório de participações.
    private final ParticipacaoRepository participacaoRepository;

    // Repositório de participantes.
    private final ParticipanteRepository participanteRepository;

    // Repositório de aplicações.
    private final AplicacaoRepository aplicacaoRepository;

    // Repositório responsável por salvar e consultar respostas.
    private final RespostaRepository respostaRepository;

    // Repositório responsável por consultar perguntas.
    private final PerguntaRepository perguntaRepository;

    // Serviço responsável pela geração dos resultados.
    private final ResultadoService resultadoService;

    // Construtor utilizado pelo Spring para disponibilizar
    // as dependências necessárias ao serviço.
    public ParticipacaoService(
            ParticipacaoRepository participacaoRepository,
            ParticipanteRepository participanteRepository,
            AplicacaoRepository aplicacaoRepository,
            RespostaRepository respostaRepository,
            PerguntaRepository perguntaRepository,
            ResultadoService resultadoService) {

        this.participacaoRepository = participacaoRepository;
        this.participanteRepository = participanteRepository;
        this.aplicacaoRepository = aplicacaoRepository;
        this.respostaRepository = respostaRepository;
        this.perguntaRepository = perguntaRepository;
        this.resultadoService = resultadoService;
    }

    // Inicia uma participação ou recupera uma existente.
    @Transactional
    public ParticipacaoResponseDTO iniciarOuRecuperarParticipacao(
            Integer participanteId,
            Integer aplicacaoId) {

        // Verifica se o participante existe.
        Participante participante = participanteRepository
                .findById(participanteId)
                .orElseThrow(() ->
                        new RuntimeException("Participante não encontrado."));

        // Verifica se a aplicação existe.
        Aplicacao aplicacao = aplicacaoRepository
                .findById(aplicacaoId)
                .orElseThrow(() ->
                        new RuntimeException("Aplicação não encontrada."));

        // Procura uma participação já existente.
        Participacao participacao = participacaoRepository
                .findByParticipanteIdAndAplicacaoId(
                        participanteId,
                        aplicacaoId
                )
                .orElseGet(() -> {

                    // Cria uma nova participação caso não exista.
                    Participacao nova = new Participacao();

                    nova.setDataInicio(LocalDateTime.now());
                    nova.setStatus(StatusParticipacao.EM_ANDAMENTO);
                    nova.setParticipante(participante);
                    nova.setAplicacao(aplicacao);

                    return participacaoRepository.save(nova);
                });

        // Converte a entidade em DTO antes de retornar.
        return new ParticipacaoResponseDTO(
                participacao.getId(),
                participacao.getDataInicio(),
                participacao.getDataTermino(),
                participacao.getStatus(),
                participacao.getParticipante().getId(),
                participacao.getAplicacao().getId()
        );
    }

    /**
     * Salva uma resposta do participante.
     *
     * Se a resposta já existir para a pergunta,
     * atualiza o valor.
     *
     * Caso contrário, cria uma nova resposta.
     */
    @Transactional
    public RespostaResponseDTO salvarResposta(
            Integer participacaoId,
            RespostaCadastroDTO dados) {

        // Busca a participação.
        Participacao participacao = participacaoRepository
                .findById(participacaoId)
                .orElseThrow(() ->
                        new RuntimeException("Participação não encontrada."));

        // Impede alterações em participações concluídas.
        if (participacao.getStatus() == StatusParticipacao.CONCLUIDA) {
            throw new RuntimeException(
                    "Não é possível alterar uma participação concluída."
            );
        }

        // Busca a pergunta informada no DTO.
        Pergunta pergunta = perguntaRepository
                .findById(dados.perguntaId())
                .orElseThrow(() ->
                        new RuntimeException("Pergunta não encontrada."));

        // Identifica o questionário da participação.
        Integer questionarioId = participacao.getAplicacao()
                .getQuestionario()
                .getId();

        // Verifica se a pergunta pertence ao questionário da participação.
        if (!pergunta.getBloco().getQuestionario().getId()
                .equals(questionarioId)) {

            throw new RuntimeException(
                    "Esta pergunta não pertence ao questionário da participação."
            );
        }

        // Procura uma resposta já cadastrada para essa pergunta.
        Resposta resposta = respostaRepository
                .findByParticipacaoIdAndPerguntaId(
                        participacaoId,
                        dados.perguntaId()
                )
                .orElseGet(() -> {

                    // Cria uma resposta nova se ainda não existir.
                    Resposta nova = new Resposta();

                    nova.setParticipacao(participacao);
                    nova.setPergunta(pergunta);

                    return nova;
                });

        // Atualiza o valor da resposta.
        resposta.setValor(dados.valor());

        // Salva no banco.
        Resposta respostaSalva = respostaRepository.save(resposta);

        // Retorna os dados através do DTO.
        return new RespostaResponseDTO(
                respostaSalva.getId(),
                respostaSalva.getValor(),
                respostaSalva.getPergunta().getId(),
                respostaSalva.getParticipacao().getId()
        );
    }

    // Consulta todas as respostas de uma participação.
    @Transactional(readOnly = true)
    public List<RespostaResponseDTO> buscarRespostasDaParticipacao(
            Integer participacaoId) {

        // Verifica se a participação existe.
        participacaoRepository.findById(participacaoId)
                .orElseThrow(() ->
                        new RuntimeException("Participação não encontrada."));

        // Busca todas as respostas da participação.
        List<Resposta> respostas =
                respostaRepository.findByParticipacaoId(participacaoId);

        // Converte cada entidade Resposta em RespostaResponseDTO.
        return respostas.stream()
                .map(resposta -> new RespostaResponseDTO(
                        resposta.getId(),
                        resposta.getValor(),
                        resposta.getPergunta().getId(),
                        resposta.getParticipacao().getId()
                ))
                .collect(Collectors.toList());
    }

    // Finaliza uma participação somente quando todas as perguntas foram respondidas.
    @Transactional
    public ParticipacaoResponseDTO finalizarParticipacao(Integer participacaoId) {

        // Busca a participação.
        Participacao participacao = participacaoRepository.findById(participacaoId)
                .orElseThrow(() ->
                        new RuntimeException("Participação não encontrada."));

        // Verifica se já foi concluída.
        if (participacao.getStatus() == StatusParticipacao.CONCLUIDA) {
            throw new RuntimeException("Esta participação já foi concluída.");
        }

        // Identifica o questionário da aplicação.
        Integer questionarioId = participacao.getAplicacao()
                .getQuestionario()
                .getId();

        // Busca todas as perguntas do questionário.
        List<Pergunta> perguntas =
                perguntaRepository.findByBlocoQuestionarioId(questionarioId);

        // Impede a conclusão de questionários sem perguntas.
        if (perguntas.isEmpty()) {
            throw new RuntimeException(
                    "O questionário não possui perguntas cadastradas."
            );
        }

        // Busca as respostas já registradas.
        List<Resposta> respostas =
                respostaRepository.findByParticipacaoId(participacaoId);

        // Guarda os IDs das perguntas respondidas.
        Set<Integer> perguntasRespondidas = respostas.stream()
                .map(resposta -> resposta.getPergunta().getId())
                .collect(Collectors.toSet());

        // Verifica se todas as perguntas foram respondidas.
        boolean todasRespondidas = perguntas.stream()
                .allMatch(pergunta ->
                        perguntasRespondidas.contains(pergunta.getId()));

        if (!todasRespondidas) {
            throw new RuntimeException(
                    "Não é possível finalizar: existem perguntas sem resposta."
            );
        }

        // Registra a data de término e altera o status.
        participacao.setDataTermino(LocalDateTime.now());
        participacao.setStatus(StatusParticipacao.CONCLUIDA);

        // Salva a participação como concluída.
        participacaoRepository.save(participacao);

        // Gera automaticamente o resultado da participação.
        resultadoService.gerarResultado(participacaoId);

        // Retorna os dados da participação concluída.
        return new ParticipacaoResponseDTO(
                participacao.getId(),
                participacao.getDataInicio(),
                participacao.getDataTermino(),
                participacao.getStatus(),
                participacao.getParticipante().getId(),
                participacao.getAplicacao().getId()
        );
    }
}
