package br.com.psicossocial.Repository.BlocoRepository;

import br.com.psicossocial.entity.Questionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlocoRepository extends JpaRepository<Questionario,Integer> {
}
