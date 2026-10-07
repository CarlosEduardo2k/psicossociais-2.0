package br.com.psicossocial.service;
import br.com.psicossocial.entity.Questionario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionarioRepository {
    private final br.com.psicossocial.repository.QuestionarioRepository repository;

    public QuestionarioRepository(br.com.psicossocial.repository.QuestionarioRepository repository) {
        this.repository = repository;
    }
    public Questionario cadastrar(Questionario questionario) {

        if (repository.existsByNome(questionario.getNome())) {
            throw new RuntimeException("Já existe um questionário com esse nome");

        }

        return repository.save(questionario);
    }
    public List<Questionario> listarTodos (){
        return repository.findAll();
    }

}
