package br.com.psicossocial.repository;

import br.com.psicossocial.entity.Administrador;
import br.com.psicossocial.enums.PerfilAdministrador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByEmailAndIdNot(String email, Integer id);
    long countByPerfil (PerfilAdministrador perfil);
    Optional<Administrador> findByCpf(String Cpf);
}
