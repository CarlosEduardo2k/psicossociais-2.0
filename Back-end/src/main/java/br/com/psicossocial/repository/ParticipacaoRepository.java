
package br.com.psicossocial.repository;

// Importa a entidade que representa a participação
// de uma pessoa em uma aplicação.
import br.com.psicossocial.entity.Participacao;

// Permite realizar operações prontas no banco de dados,
// como salvar, buscar, atualizar e excluir registros.
import org.springframework.data.jpa.repository.JpaRepository;

// Permite representar um resultado que pode existir
// ou não no banco de dados.
import java.util.Optional;

// Repository responsável pelo acesso aos dados
// da entidade Participacao.
public interface ParticipacaoRepository
        extends JpaRepository<Participacao, Integer> {

    // Busca uma participação pelo participante
    // e pela aplicação aos quais ela pertence.
    //
    // Isso permite verificar se a pessoa já começou
    // aquele questionário antes de criar outra participação.
    Optional<Participacao> findByParticipanteIdAndAplicacaoId(
            Integer participanteId,
            Integer aplicacaoId
    );
}
