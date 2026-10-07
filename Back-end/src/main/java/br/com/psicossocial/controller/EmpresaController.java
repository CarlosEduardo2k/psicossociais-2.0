package br.com.psicossocial.controller;

import br.com.psicossocial.dto.EmpresaCadastroDTO;
import br.com.psicossocial.dto.EmpresaEdicaoDTO;
import br.com.psicossocial.dto.EmpresaResponseDTO;
import br.com.psicossocial.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Expõe os endpoints HTTP responsáveis pelo ciclo de vida das empresas.
 * <p>
 * A classe recebe as requisições, valida os dados de entrada e delega as regras
 * de negócio para {@link EmpresaService}. Os DTOs evitam que as entidades do
 * domínio sejam expostas diretamente pela API.
 * </p>
 */
@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    /** Serviço que centraliza as regras de negócio das empresas. */
    private final EmpresaService empresaService;

    /**
     * Injeta o serviço utilizado pelos endpoints deste controller.
     *
     * @param empresaService serviço de empresas
     */
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    /**
     * Cria uma nova empresa.
     * <p>
     * O corpo da requisição é validado antes de o cadastro ser encaminhado ao
     * serviço. Em caso de sucesso, a API devolve os dados criados com status 201.
     * </p>
     *
     * @param dadosCadastro dados necessários para cadastrar a empresa
     * @return a empresa cadastrada e o status HTTP {@code 201 Created}
     */
    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> cadastrar(@RequestBody @Valid EmpresaCadastroDTO dadosCadastro){
        EmpresaResponseDTO empresa = empresaService.cadastrar(dadosCadastro);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
    }

    /**
     * Busca uma empresa pelo seu identificador.
     *
     * @param id identificador da empresa informado na URL
     * @return os dados da empresa encontrada e o status HTTP {@code 200 OK}
     */
    @GetMapping("/{id}")
    public  ResponseEntity<EmpresaResponseDTO> buscarPorId(@PathVariable Integer id){
        EmpresaResponseDTO empresa = empresaService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(empresa);
    }

    /**
     * Atualiza os dados de uma empresa existente.
     * <p>
     * O identificador define qual empresa será alterada, enquanto o corpo da
     * requisição contém os novos dados, que são validados antes da atualização.
     * </p>
     *
     * @param id identificador da empresa a ser atualizada
     * @param dadosEdicao dados que devem ser atualizados
     * @return a empresa atualizada e o status HTTP {@code 200 OK}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid EmpresaEdicaoDTO dadosEdicao){
        EmpresaResponseDTO empresa = empresaService.atualizar(id, dadosEdicao);
        return ResponseEntity.status(HttpStatus.OK).body(empresa);
    }
}
