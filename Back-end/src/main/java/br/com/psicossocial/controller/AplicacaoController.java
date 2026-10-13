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
 * de negócio para. Os DTOs evitam que as entidades do domínio sejam expostas diretamente pela API.
 * </p>
 */
@RestController
@RequestMapping("/aplicacoes")
public class AplicacaoController {

    private final AplicacaoService aplicacaoService;

    public AplicacaoController(AplicacaoService aplicacaoService) {
        this.aplicacaoService = aplicacaoService;
    }
    @PostMapping
    public ResponseEntity<AplicacaoResponseDTO> cadastrar(@RequestBody @Valid AplicacaoCadastroDTO dadosCadastro){
        AplicacaoResponseDTO aplicacao = aplicacaoService.cadastrar(dadosCadastro);
        return ResponseEntity.status(HttpStatus.CREATED).body(aplicacao);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AplicacaoResponseDTO> buscarPorId(@PathVariable Integer id){
        AplicacaoResponseDTO aplicacao = aplicacaoService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(aplicacao);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AplicacaoResponseDTO> atualizar(@RequestBody @Valid AplicacaoEdicaoDTO dadosEdicao, @PathVariable Integer id){
        AplicacaoResponseDTO aplicacao = aplicacaoService.atualizar(id, dadosEdicao);
        return ResponseEntity.status(HttpStatus.OK).body(aplicacao);
    }

    // endpoint de abertura e fechamento de uma aplicação.
    @PutMapping("/{id}/fechar")
    public ResponseEntity<AplicacaoResponseDTO> fechar(@PathVariable Integer id){
        AplicacaoResponseDTO aplicacao = aplicacaoService.fechar(id);
        return ResponseEntity.ok(aplicacao);
    }

    @PutMapping("/{id}/abrir")
    public ResponseEntity<AplicacaoResponseDTO> abrir(@PathVariable Integer id){
        AplicacaoResponseDTO aplicacao = aplicacaoService.abrir(id);
        return ResponseEntity.ok(aplicacao);
    }
}
