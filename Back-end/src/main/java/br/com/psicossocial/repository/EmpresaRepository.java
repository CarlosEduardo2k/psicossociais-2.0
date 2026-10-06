package br.com.psicossocial.repository;

import br.com.psicossocial.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository  extends JpaRepository<Empresa, Integer> {
    boolean existsByCnpj(String cnpj);
    boolean existsByCnpjAndIdNot(String cnpj, Integer id);
}
