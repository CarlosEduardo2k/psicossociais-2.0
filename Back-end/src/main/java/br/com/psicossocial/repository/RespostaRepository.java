
package br.com.psicossocial.repository;

// Importa a entidade que representa
// uma resposta registrada no questionário.
import br.com.psicossocial.entity.Resposta;

// Importa os métodos prontos de acesso ao banco de dados.
import org.springframework.data.jpa.repository.JpaRepository;

// Permite retornar uma resposta caso ela exista
// ou indicar que ela não foi encontrada.
import java.util.Optional;

// Permite retornar uma lista de respostas.
import java.util.List;

// Interface responsável pelo acesso aos dados
// da entidade Resposta.
public interface RespostaRepository
        extends JpaRepository<Resposta, Integer> {

    // Busca todas as respostas de uma participação.
    // Será utilizado para recuperar o progresso
    // do participante ao retornar ao questionário.
    List<Resposta> findByParticipacaoId(Integer participacaoId);

    // Busca uma resposta específica pela participação
    // e pela pergunta.
    //
    // Se já existir, poderemos atualizar seu valor.
    // Se não existir, poderemos cadastrar uma nova.
    Optional<Resposta> findByParticipacaoIdAndPerguntaId(
            Integer participacaoId,
            Integer perguntaId
    );
}
