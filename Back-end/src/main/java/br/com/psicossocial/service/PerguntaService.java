package br.com.psicossocial.service;

import br.com.psicossocial.entity.Pergunta;
import br.com.psicossocial.repository.PerguntaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerguntaService {
    private PerguntaRepository repository;

    public PerguntaService(PerguntaRepository repository){
        this.repository = repository;
    }
    public Pergunta cadastrarPergunta(Pergunta pergunta){
        if (repository.existsByTextoAndBlocoId(
                pergunta.getTexto(),
                pergunta.getBloco().getId()
            )
        ){
        throw new RuntimeException("esse texto ja pertence a esse bloco");
        }
        if (repository.existsByOrdemInBlocoId(
                pergunta.getOrdem(),
                pergunta.getBloco().getId()
        )){
            throw new RuntimeException("a orden da pergunta ja esta ocupada");
        }
        return repository.save(pergunta);
    }
    public List<Pergunta> findByTexto(String texto){
        return repository.findByTexto(texto);
    }
}
