package br.com.psicossocial.controller;

import br.com.psicossocial.dto.LoginDTO;
import br.com.psicossocial.dto.LoginResponseDTO;
import br.com.psicossocial.service.AutenticacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {
    private final AutenticacaoService autenticacaoService;

    public  AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }
    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO dadosLogin){
        LoginResponseDTO resposta = autenticacaoService.validarLogin(dadosLogin);
        return ResponseEntity.ok(resposta);
    }
}
