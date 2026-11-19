package br.com.psicossocial.controller;


import br.com.psicossocial.entity.Bloco;
import br.com.psicossocial.service.BlocoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bloco")
public class BlocoController{
    private final BlocoService BlocoService;

    public BlocoController(BlocoService BlocoService) {
        this.BlocoService = BlocoService;
    }

    @PostMapping
    public ResponseEntity<Bloco> cadastrarBloco(@RequestBody Bloco bloco){
        BlocoService.cadastrarBloco(bloco);
        return ResponseEntity.status(HttpStatus.CREATED).body(bloco);
    }
}
