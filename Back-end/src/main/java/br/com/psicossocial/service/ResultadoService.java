
package br.com.psicossocial.service;

// Importa as entidades utilizadas pelo serviço.
import br.com.psicossocial.entity.Resultado;
import br.com.psicossocial.entity.Participacao;
import br.com.psicossocial.entity.Resposta;
import br.com.psicossocial.enums.StatusParticipacao;

// Importa o DTO de resposta.
import br.com.psicossocial.dto.ResultadoResponseDTO;

// Importa os repositórios.
import br.com.psicossocial.repository.ResultadoRepository;
import br.com.psicossocial.repository.ParticipacaoRepository;
import br.com.psicossocial.repository.RespostaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

// Indica que esta classe contém as regras de negócio
// relacionadas à geração e consulta dos resultados.
@Service
public class ResultadoService {

    private final ResultadoRepository resultadoRepository;
    private final ParticipacaoRepository participacaoRepository;
    private final RespostaRepository respostaRepository;

    // Construtor utilizado pelo Spring para injetar os repositórios.
    public ResultadoService(
            ResultadoRepository resultadoRepository,
            ParticipacaoRepository participacaoRepository,
            RespostaRepository respostaRepository
    ) {
        this.resultadoRepository = resultadoRepository;
        this.participacaoRepository = participacaoRepository;
        this.respostaRepository = respostaRepository;
    }

    // Calcula e salva o resultado de uma participação.
    @Transactional
    public ResultadoResponseDTO gerarResultado(Integer participacaoId) {

        // Busca a participação.
        Participacao participacao = participacaoRepository
                .findById(participacaoId)
                .orElseThrow(() ->
                        new RuntimeException("Participação não encontrada."));

        // Verifica se a participação foi concluída.
        if (participacao.getStatus() != StatusParticipacao.CONCLUIDA) {
            throw new RuntimeException(
                    "Não é possível gerar o resultado de uma participação em andamento."
            );
        }

        // Verifica se já existe um resultado para essa participação.
        Resultado resultado = resultadoRepository
                .findByParticipacaoId(participacaoId)
                .orElse(null);

        // Se já existir, retorna o resultado cadastrado.
        if (resultado != null) {
            return converterParaDTO(resultado);
        }

        // Busca as respostas da participação.
        List<Resposta> respostas =
                respostaRepository.findByParticipacaoId(participacaoId);

        if (respostas.isEmpty()) {
            throw new RuntimeException(
                    "Não existem respostas para calcular o resultado."
            );
        }

        // Soma os valores das respostas.
        BigDecimal soma = respostas.stream()
                .map(resposta -> BigDecimal.valueOf(resposta.getValor()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcula a média das respostas.
        BigDecimal media = soma.divide(
                BigDecimal.valueOf(respostas.size()),
                2,
                RoundingMode.HALF_UP
        );

        // Cria e preenche o resultado.
        resultado = new Resultado();
        resultado.setMediaGeral(media);
        resultado.setParticipacao(participacao);

        // Salva o resultado.
        Resultado resultadoSalvo = resultadoRepository.save(resultado);

        return converterParaDTO(resultadoSalvo);
    }

    // Consulta o resultado de uma participação.
    @Transactional(readOnly = true)
    public ResultadoResponseDTO buscarResultadoPorParticipacao(
            Integer participacaoId) {

        // Procura o resultado relacionado à participação.
        Resultado resultado = resultadoRepository
                .findByParticipacaoId(participacaoId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Resultado não encontrado para esta participação."
                        ));

        // Converte a entidade em DTO.
        return converterParaDTO(resultado);
    }

    // Converte a entidade Resultado para DTO.
    private ResultadoResponseDTO converterParaDTO(Resultado resultado) {
        return new ResultadoResponseDTO(
                resultado.getId(),
                resultado.getMediaGeral(),
                resultado.getParticipacao().getId()
        );
    }
}
