package br.com.psicossocial.controller;

import br.com.psicossocial.entity.Questionario;
import br.com.psicossocial.service.QuestionarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questionario")
public class QuestionarioController {
    private final QuestionarioService service;

    public QuestionarioController(QuestionarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Questionario> listartodos(){
        return service.listarTodos();
    }

    @PostMapping
    public Questionario cadastrar(@RequestBody Questionario questionario){
        return service.cadastrar(questionario);
    }
}
