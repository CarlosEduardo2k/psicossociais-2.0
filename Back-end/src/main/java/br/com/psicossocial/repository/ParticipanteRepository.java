
package br.com.psicossocial.repository;

// Importa a entidade Participante,
// que representa o funcionário que responderá ao questionário.
import br.com.psicossocial.entity.Participante;

// Importa a interface do Spring Data JPA,
// que fornece operações prontas para o banco de dados.
import org.springframework.data.jpa.repository.JpaRepository;

// Permite representar um participante que pode ou não existir.
import java.util.Optional;

// Interface responsável pelo acesso aos dados
// da entidade Participante.
public interface ParticipanteRepository
        extends JpaRepository<Participante, Integer> {

    // Procura um participante pelo CPF.
    // Será útil para verificar se ele já está cadastrado.
    Optional<Participante> findByCpf(String cpf);

}
