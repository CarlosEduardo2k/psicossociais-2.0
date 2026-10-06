package br.com.psicossocial.repository;

import br.com.psicossocial.entity.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerguntaRepository extends JpaRepository<Pergunta, Integer> {
    List<Pergunta> findByTexto(String texto);

    boolean existsByTextoAndBlocoId(String texto, Integer blocoId);
    boolean existsByOrdemInBlocoId(Integer orden, Integer blocoId);
}
