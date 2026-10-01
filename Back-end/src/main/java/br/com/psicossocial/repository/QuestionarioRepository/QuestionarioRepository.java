package br.com.psicossocial.Repository.QuestionarioRepository;

import br.com.psicossocial.entity.Questionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface QuestionarioRepository extends JpaRepository<Questionario,Integer> {

    List<Questionario> findByNome(String nome);

}

