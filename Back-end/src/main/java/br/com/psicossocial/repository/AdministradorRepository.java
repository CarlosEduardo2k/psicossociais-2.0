package br.com.psicossocial.repository;

import br.com.psicossocial.entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepository  extends JpaRepository<Administrador,Integer> {

    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

}
