package br.com.psicossocial.controller;

import br.com.psicossocial.service.PerguntaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/pergunta")
public class PerguntaController {
    private final PerguntaService service;

    public PerguntaController(PerguntaService service) {
        this.service = service;
    }

}
