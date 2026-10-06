package br.com.psicossocial.repository;

import br.com.psicossocial.entity.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerguntaRepository extends JpaRepository<Pergunta, Integer> {
    List<Pergunta> findByNome(String nome);
}
