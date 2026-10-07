package br.com.psicossocial.controller;

import br.com.psicossocial.dto.AplicacaoCadastroDTO;
import br.com.psicossocial.dto.AplicacaoEdicaoDTO;
import br.com.psicossocial.dto.AplicacaoResponseDTO;
import br.com.psicossocial.service.AplicacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Expõe os endpoints HTTP responsáveis pelo ciclo de vida das aplicações.
 * <p>
 * A classe recebe as requisições, valida os dados de entrada e delega as regras
 * de negócio para {@link AplicacaoService}. Os DTOs evitam que as entidades do
 * domínio sejam expostas diretamente pela API.
 * </p>
 */
@RestController
@RequestMapping("/aplicacoes")
public class AplicacaoController {

    /** Serviço que centraliza as regras de negócio das aplicações. */
    private final AplicacaoService aplicacaoService;

    /**
     * Injeta o serviço utilizado pelos endpoints deste controller.
     *
     * @param aplicacaoService serviço de aplicações
     */
    public AplicacaoController(AplicacaoService aplicacaoService) {
        this.aplicacaoService = aplicacaoService;
    }

    /**
     * Cria uma nova aplicação.
     * <p>
     * O corpo da requisição é validado antes de o cadastro ser encaminhado ao
     * serviço. Em caso de sucesso, a API devolve os dados criados com status 201.
     * </p>
     *
     * @param dadosCadastro dados necessários para cadastrar a aplicação
     * @return a aplicação cadastrada e o status HTTP {@code 201 Created}
     */
    @PostMapping
    public ResponseEntity<AplicacaoResponseDTO> cadastrar(@RequestBody @Valid AplicacaoCadastroDTO dadosCadastro){
        AplicacaoResponseDTO aplicacao = aplicacaoService.cadastrar(dadosCadastro);
        return ResponseEntity.status(HttpStatus.CREATED).body(aplicacao);
    }

    /**
     * Busca uma aplicação pelo seu identificador.
     *
     * @param id identificador da aplicação informado na URL
     * @return os dados da aplicação encontrada e o status HTTP {@code 200 OK}
     */
    @GetMapping("/{id}")
    public ResponseEntity<AplicacaoResponseDTO> buscarPorId(@PathVariable Integer id){
        AplicacaoResponseDTO aplicacao = aplicacaoService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(aplicacao);
    }

    /**
     * Atualiza os dados de uma aplicação existente.
     * <p>
     * O identificador define qual aplicação será alterada, enquanto o corpo da
     * requisição contém os novos dados, que são validados antes da atualização.
     * </p>
     *
     * @param dadosEdicao dados que devem ser atualizados
     * @param id identificador da aplicação a ser atualizada
     * @return a aplicação atualizada e o status HTTP {@code 200 OK}
     */
    @PutMapping("/{id}")
    public ResponseEntity<AplicacaoResponseDTO> atualizar(@RequestBody @Valid AplicacaoEdicaoDTO dadosEdicao, @PathVariable Integer id){
        AplicacaoResponseDTO aplicacao = aplicacaoService.atualizar(id, dadosEdicao);
        return ResponseEntity.status(HttpStatus.OK).body(aplicacao);
    }
}
