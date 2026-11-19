
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
import br.com.psicossocial.enums.StatusParticipacao;

// Repositórios utilizados pelo serviço.
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

@Service
public class ParticipacaoService {

    private final ParticipacaoRepository participacaoRepository;
    private final ParticipanteRepository participanteRepository;
    private final AplicacaoRepository aplicacaoRepository;
    private final RespostaRepository respostaRepository;
    private final PerguntaRepository perguntaRepository;
    private final ResultadoService resultadoService;

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

    // Converte uma participação em DTO de resposta.
    private ParticipacaoResponseDTO converterParticipacaoParaDTO(
            Participacao participacao) {

        return new ParticipacaoResponseDTO(
                participacao.getId(),
                participacao.getDataInicio(),
                participacao.getDataTermino(),
                participacao.getStatus(),
                participacao.getParticipante().getId(),
                participacao.getAplicacao().getId()
        );
    }

    // Inicia uma participação ou recupera uma existente.
    @Transactional
    public ParticipacaoResponseDTO iniciarOuRecuperarParticipacao(
            Integer participanteId,
            Integer aplicacaoId) {

        Participante participante = participanteRepository
                .findById(participanteId)
                .orElseThrow(() ->
                        new RuntimeException("Participante não encontrado."));

        Aplicacao aplicacao = aplicacaoRepository
                .findById(aplicacaoId)
                .orElseThrow(() ->
                        new RuntimeException("Aplicação não encontrada."));

        Participacao participacao = participacaoRepository
                .findByParticipanteIdAndAplicacaoId(
                        participanteId,
                        aplicacaoId
                )
                .orElseGet(() -> {

                    Participacao nova = new Participacao();

                    nova.setDataInicio(LocalDateTime.now());
                    nova.setStatus(StatusParticipacao.EM_ANDAMENTO);
                    nova.setParticipante(participante);
                    nova.setAplicacao(aplicacao);

                    return participacaoRepository.save(nova);
                });

        return converterParticipacaoParaDTO(participacao);
    }

    // Salva uma resposta nova ou atualiza uma existente.
    @Transactional
    public RespostaResponseDTO salvarResposta(
            Integer participacaoId,
            RespostaCadastroDTO dados) {

        Participacao participacao = participacaoRepository
                .findById(participacaoId)
                .orElseThrow(() ->
                        new RuntimeException("Participação não encontrada."));

        if (participacao.getStatus() == StatusParticipacao.CONCLUIDA) {
            throw new RuntimeException(
                    "Não é possível alterar uma participação concluída."
            );
        }

        Pergunta pergunta = perguntaRepository
                .findById(dados.perguntaId())
                .orElseThrow(() ->
                        new RuntimeException("Pergunta não encontrada."));

        // Verifica se a pergunta pertence ao questionário da participação.
        Integer questionarioId = participacao.getAplicacao()
                .getQuestionario()
                .getId();

        if (!pergunta.getBloco().getQuestionario().getId()
                .equals(questionarioId)) {

            throw new RuntimeException(
                    "Esta pergunta não pertence ao questionário da participação."
            );
        }

        Resposta resposta = respostaRepository
                .findByParticipacaoIdAndPerguntaId(
                        participacaoId,
                        dados.perguntaId()
                )
                .orElseGet(() -> {

                    Resposta nova = new Resposta();

                    nova.setParticipacao(participacao);
                    nova.setPergunta(pergunta);

                    return nova;
                });

        resposta.setValor(dados.valor());

        Resposta respostaSalva = respostaRepository.save(resposta);

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

        participacaoRepository.findById(participacaoId)
                .orElseThrow(() ->
                        new RuntimeException("Participação não encontrada."));

        List<Resposta> respostas =
                respostaRepository.findByParticipacaoId(participacaoId);

        return respostas.stream()
                .map(resposta -> new RespostaResponseDTO(
                        resposta.getId(),
                        resposta.getValor(),
                        resposta.getPergunta().getId(),
                        resposta.getParticipacao().getId()
                ))
                .toList();
    }

    // Finaliza uma participação quando todas as perguntas foram respondidas.
    @Transactional
    public ParticipacaoResponseDTO finalizarParticipacao(
            Integer participacaoId) {

        Participacao participacao = participacaoRepository
                .findById(participacaoId)
                .orElseThrow(() ->
                        new RuntimeException("Participação não encontrada."));

        if (participacao.getStatus() == StatusParticipacao.CONCLUIDA) {
            throw new RuntimeException(
                    "Esta participação já foi concluída."
            );
        }

        Integer questionarioId = participacao.getAplicacao()
                .getQuestionario()
                .getId();

        List<Pergunta> perguntas =
                perguntaRepository.findByBlocoQuestionarioId(questionarioId);

        if (perguntas.isEmpty()) {
            throw new RuntimeException(
                    "O questionário não possui perguntas cadastradas."
            );
        }

        List<Resposta> respostas =
                respostaRepository.findByParticipacaoId(participacaoId);

        Set<Integer> perguntasRespondidas = respostas.stream()
                .map(resposta -> resposta.getPergunta().getId())
                .collect(Collectors.toSet());

        boolean todasRespondidas = perguntas.stream()
                .allMatch(pergunta ->
                        perguntasRespondidas.contains(pergunta.getId()));

        if (!todasRespondidas) {
            throw new RuntimeException(
                    "Não é possível finalizar: existem perguntas sem resposta."
            );
        }

        participacao.setDataTermino(LocalDateTime.now());
        participacao.setStatus(StatusParticipacao.CONCLUIDA);

        participacaoRepository.save(participacao);

        resultadoService.gerarResultado(participacaoId);

        return converterParticipacaoParaDTO(participacao);
    }
}
