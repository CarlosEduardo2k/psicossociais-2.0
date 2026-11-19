
package br.com.psicossocial.controller;

import br.com.psicossocial.dto.ResultadoResponseDTO;
import br.com.psicossocial.service.ResultadoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resultados")
public class ResultadoController {

    private final ResultadoService resultadoService;

    // Injeta o serviço responsável pelos resultados.
    public ResultadoController(ResultadoService resultadoService) {
        this.resultadoService = resultadoService;
    }

    // Gera o resultado de uma participação concluída.
    @PostMapping("/gerar/{participacaoId}")
    public ResponseEntity<ResultadoResponseDTO> gerarResultado(
            @PathVariable Integer participacaoId) {

        ResultadoResponseDTO resultado =
                resultadoService.gerarResultado(participacaoId);

        return ResponseEntity.ok(resultado);
    }
    @GetMapping("/participacao/{participacaoId}")
    public ResponseEntity<ResultadoResponseDTO> buscarResultado(
            @PathVariable Integer participacaoId) {

        ResultadoResponseDTO resultado =
                resultadoService.buscarResultadoPorParticipacao(participacaoId);

        return ResponseEntity.ok(resultado);
    }
}
