package br.com.psicossocial.repository;

import br.com.psicossocial.entity.Bloco;
import br.com.psicossocial.entity.Questionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlocoRepository extends JpaRepository<Bloco,Integer> {
    boolean existsByNomeAndQuestionarioId(String nome, Integer questionarioId);
    boolean existsByOrdemAndQuestionarioId(Integer OrdemId, Integer QuestionarioId);

}
