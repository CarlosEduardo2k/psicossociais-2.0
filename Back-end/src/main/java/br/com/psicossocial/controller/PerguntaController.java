
package br.com.psicossocial.controller;

import br.com.psicossocial.entity.Pergunta;
import br.com.psicossocial.service.PerguntaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blocos/{blocoId}/perguntas")
public class PerguntaController {

    private final PerguntaService service;

    public PerguntaController(PerguntaService service) {
        this.service = service;
    }

    @PostMapping
    public Pergunta cadastrar(
            @PathVariable Integer blocoId,
            @RequestBody Pergunta pergunta) {

        return service.cadastrarPergunta(blocoId, pergunta);
    }

    @GetMapping
    public List<Pergunta> listarPorBloco(
            @PathVariable Integer blocoId) {

        return service.listarPorBloco(blocoId);
    }

    @GetMapping("/buscar")
    public List<Pergunta> buscarPorTexto(
            @RequestParam String texto) {

        return service.findByTexto(texto);
    }
}
