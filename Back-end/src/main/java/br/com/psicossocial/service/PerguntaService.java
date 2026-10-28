package br.com.psicossocial.service;

import br.com.psicossocial.entity.Bloco;
import br.com.psicossocial.entity.Pergunta;
import br.com.psicossocial.repository.BlocoRepository;
import br.com.psicossocial.repository.PerguntaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerguntaService {

    private final PerguntaRepository repository;
    private final BlocoRepository blocoRepository;

    public PerguntaService(
            PerguntaRepository repository,
            BlocoRepository blocoRepository) {
        this.repository = repository;
        this.blocoRepository = blocoRepository;
    }

    public Pergunta cadastrarPergunta(Integer blocoId, Pergunta pergunta) {

        Bloco bloco = blocoRepository.findById(blocoId)
                .orElseThrow(() ->
                        new RuntimeException("Bloco não encontrado"));

        pergunta.setBloco(bloco);

        if (repository.existsByTextoAndBlocoId(
                pergunta.getTexto(), blocoId)) {
            throw new RuntimeException(
                    "Esse texto já pertence a esse bloco");
        }

        if (repository.existsByOrdemAndBlocoId(
                pergunta.getOrdem(), blocoId)) {
            throw new RuntimeException(
                    "A ordem da pergunta já está ocupada");
        }

        return repository.save(pergunta);
    }

    public List<Pergunta> listarPorBloco(Integer blocoId) {
        return repository.findByBlocoId(blocoId);
    }

    public List<Pergunta> findByTexto(String texto) {
        return repository.findByTexto(texto);
    }
}