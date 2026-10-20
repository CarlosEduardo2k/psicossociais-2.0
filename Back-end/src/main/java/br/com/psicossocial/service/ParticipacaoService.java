
package br.com.psicossocial.service;

// Importa as entidades utilizadas pelo serviço.
import br.com.psicossocial.entity.Aplicacao;
import br.com.psicossocial.entity.Participacao;
import br.com.psicossocial.entity.Participante;
import br.com.psicossocial.entity.StatusParticipacao;
import br.com.psicossocial.entity.Resposta;
import br.com.psicossocial.entity.Pergunta;
import br.com.psicossocial.repository.RespostaRepository;
import br.com.psicossocial.repository.PerguntaRepository;

// Importa os repositórios responsáveis pelo acesso ao banco.
import br.com.psicossocial.repository.AplicacaoRepository;
import br.com.psicossocial.repository.ParticipacaoRepository;
import br.com.psicossocial.repository.ParticipanteRepository;

// Importa a anotação que identifica um serviço do Spring.
import org.springframework.stereotype.Service;

// Permite executar as operações dentro de uma transação.
import org.springframework.transaction.annotation.Transactional;

// Importa a classe para trabalhar com datas e horários.
import java.time.LocalDateTime;

// Indica que esta classe contém regras de negócio
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

    // Construtor utilizado pelo Spring para disponibilizar
    // os repositórios necessários ao serviço.
    public ParticipacaoService(
            ParticipacaoRepository participacaoRepository,
            ParticipanteRepository participanteRepository,
            AplicacaoRepository aplicacaoRepository,
            RespostaRepository respostaRepository,
            PerguntaRepository perguntaRepository
    ) {
        this.participacaoRepository = participacaoRepository;
        this.participanteRepository = participanteRepository;
        this.aplicacaoRepository = aplicacaoRepository;
        this.respostaRepository = respostaRepository;
        this.perguntaRepository = perguntaRepository;
    }

    // Inicia uma participação ou recupera uma existente.
    @Transactional
    public Participacao iniciarOuRecuperarParticipacao(
            Integer participanteId,
            Integer aplicacaoId
    ) {

        // Procura o participante pelo ID.
        // Se não existir, interrompe a operação com uma mensagem.
        Participante participante = participanteRepository
                .findById(participanteId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Participante não encontrado."
                        )
                );

        // Procura a aplicação pelo ID.
        Aplicacao aplicacao = aplicacaoRepository
                .findById(aplicacaoId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Aplicação não encontrada."
                        )
                );

        // Verifica se já existe uma participação
        // desse participante nessa aplicação.
        var participacaoExistente = participacaoRepository
                .findByParticipanteIdAndAplicacaoId(
                        participanteId,
                        aplicacaoId
                );

        // Se já existir, retorna a participação encontrada.
        // Isso permite utilizar a participação anterior
        // quando o participante voltar ao questionário.
        if (participacaoExistente.isPresent()) {
            return participacaoExistente.get();
        }

        // Cria uma nova participação.
        Participacao participacao = new Participacao();

        // Registra a data e o horário do início.
        participacao.setDataInicio(LocalDateTime.now());

        // Define o status inicial da participação.
        participacao.setStatus(StatusParticipacao.EM_ANDAMENTO);

        // Associa o participante à participação.
        participacao.setParticipante(participante);

        // Associa a aplicação à participação.
        participacao.setAplicacao(aplicacao);

        // Salva a participação no banco de dados
        // e retorna o registro criado.
        return participacaoRepository.save(participacao);
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
    public Resposta salvarResposta(

    @Transactional
    public Resposta salvarResposta(
            Integer participacaoId,
            Integer perguntaId,
            Integer valor
    ) {

        // Procura a participação pelo ID.
        Participacao participacao = participacaoRepository
                .findById(participacaoId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Participação não encontrada."
                        )
                );

        // Não permite alterar respostas de uma participação concluída.
        if (participacao.getStatus() == StatusParticipacao.CONCLUIDA) {
            throw new RuntimeException(
                    "A participação já foi concluída."
            );
        }

        // Procura a pergunta pelo ID.
        Pergunta pergunta = perguntaRepository
                .findById(perguntaId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pergunta não encontrada."
                        )
                );

        // Procura uma resposta existente para essa pergunta
        // dentro da participação informada.
        var respostaExistente = respostaRepository
                .findByParticipacaoIdAndPerguntaId(
                        participacaoId,
                        perguntaId
                );

        Resposta resposta;

        if (respostaExistente.isPresent()) {

            // A resposta já existe: recupera o registro
            // para atualizar seu valor.
            resposta = respostaExistente.get();

        } else {

            // A resposta ainda não existe: cria um registro.
            resposta = new Resposta();

            // Relaciona a resposta à participação.
            resposta.setParticipacao(participacao);

            // Relaciona a resposta à pergunta.
            resposta.setPergunta(pergunta);
        }

        // Define o valor informado pelo participante.
        resposta.setValor(valor);

        // Salva o registro novo ou atualizado no banco.
        return respostaRepository.save(resposta);
    }



}
