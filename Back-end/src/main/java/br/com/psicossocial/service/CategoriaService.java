package br.com.psicossocial.service;

import br.com.psicossocial.entity.Categoria;
import br.com.psicossocial.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public Categoria cadastrarCategoria(Categoria categoria){
        if (repository.existsByNome(categoria.getNome())) {
            throw new RuntimeException("Categoria ja existente");

        }
        return repository.save(categoria);
    }
    public List<Categoria> findAll(){
        return repository.findAll();
    }

}
