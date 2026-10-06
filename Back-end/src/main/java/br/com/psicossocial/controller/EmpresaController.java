package br.com.psicossocial.controller;

import br.com.psicossocial.dto.EmpresaCadastroDTO;
import br.com.psicossocial.dto.EmpresaEdicaoDTO;
import br.com.psicossocial.dto.EmpresaResponseDTO;
import br.com.psicossocial.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {
    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> cadastrar(@RequestBody @Valid EmpresaCadastroDTO dadosCadastro){
        EmpresaResponseDTO empresa = empresaService.cadastrar(dadosCadastro);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<EmpresaResponseDTO> buscarPorId(@PathVariable Integer id){
        EmpresaResponseDTO empresa = empresaService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(empresa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid EmpresaEdicaoDTO dadosEdicao){
        EmpresaResponseDTO empresa = empresaService.atualizar(id, dadosEdicao);
        return ResponseEntity.status(HttpStatus.OK).body(empresa);
    }
}
