package br.com.psicossocial.service;

import br.com.psicossocial.entity.Bloco;
import br.com.psicossocial.repository.BlocoRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BlocoService {
    private final BlocoRepository repository;

    public BlocoService(BlocoRepository blocoRepository) {
        this.repository = blocoRepository;
    }


    public Bloco cadastrarBloco(Bloco bloco) {
        if (repository.existsByNomeAndQuestionarioId(
                bloco.getNome(),
                bloco.getQuestionario().getId()
        )) {
            throw new RuntimeException("esse bloxo ja existe nesse questionario");
        }

        if (repository.existsByOrdemAndQuestionarioId(
                bloco.getOrdem(),
                bloco.getQuestionario().getId()
        )){
            throw new RuntimeException("essa posição de bloco ja esta ocupado");
        }
        return repository.save(bloco);
    }

    public List<Bloco> listarTodos(){return repository.findAll();}

}
