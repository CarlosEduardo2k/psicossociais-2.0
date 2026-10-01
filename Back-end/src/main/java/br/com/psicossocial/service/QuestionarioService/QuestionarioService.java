package br.com.psicossocial.service.QuestionarioService;
import br.com.psicossocial.Repository.QuestionarioRepository.QuestionarioRepository;
import br.com.psicossocial.entity.Questionario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionarioService {
    private final QuestionarioRepository repository;

    public QuestionarioService(QuestionarioRepository repository) {
        this.repository = repository;
    }
    public Questionario cadastrar(Questionario questionario) {
        return repository.save(questionario);
    }
    public List<Questionario> listarTodos (){
        return repository.findAll();
    }

}
