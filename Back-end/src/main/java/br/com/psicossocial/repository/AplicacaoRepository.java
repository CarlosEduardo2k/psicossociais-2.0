package br.com.psicossocial.repository;

import br.com.psicossocial.entity.Aplicacao;
import br.com.psicossocial.entity.StatusAplicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AplicacaoRepository extends JpaRepository<Aplicacao,Integer > {
    boolean existsByStatus(StatusAplicacao status);
    List<Aplicacao> findByStatus(StatusAplicacao status);
    List<Aplicacao> findAllByOrderByDataInicioDesc();
    List<Aplicacao> findByEmpresaId(Integer empresaId);
}
