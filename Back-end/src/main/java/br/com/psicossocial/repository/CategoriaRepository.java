package br.com.psicossocial.repository;

import br.com.psicossocial.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {
    boolean existsByNome(String nome);
}
