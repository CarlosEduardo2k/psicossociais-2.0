
package br.com.psicossocial.repository;

import br.com.psicossocial.entity.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultadoRepository extends JpaRepository<Resultado, Integer> {

    // Busca o resultado relacionado a uma participação.
    Optional<Resultado> findByParticipacaoId(Integer participacaoId);
}
