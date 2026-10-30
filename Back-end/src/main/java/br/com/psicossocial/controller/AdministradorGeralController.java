package br.com.psicossocial.controller;

import br.com.psicossocial.dto.AdministradorCadastroDTO;
import br.com.psicossocial.dto.AdministradorEdicaoDTO;
import br.com.psicossocial.dto.AdministradorResponseDTO;
import br.com.psicossocial.service.AdministradorGeralService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administradorGeral")
public class AdministradorGeralController {
    private final AdministradorGeralService administradorGeralService;

    public AdministradorGeralController(AdministradorGeralService administradorGeralService) {
        this.administradorGeralService = administradorGeralService;
    }

    @PostMapping
    public ResponseEntity<AdministradorResponseDTO> cadastrar(@RequestBody @Valid AdministradorCadastroDTO dadosCadastro) {
        AdministradorResponseDTO administrador = administradorGeralService.cadastrar(dadosCadastro);
        return ResponseEntity.status(HttpStatus.CREATED).body(administrador);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministradorResponseDTO> buscar(@PathVariable Integer id){
        AdministradorResponseDTO buscarAdministrador = administradorGeralService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(buscarAdministrador);
    }

    @GetMapping("/id")
    public ResponseEntity<AdministradorResponseDTO> atualizar(@RequestBody @Valid @PathVariable AdministradorEdicaoDTO dadosEdicao, Integer id){
        AdministradorResponseDTO atualizar = administradorGeralService.atualizarAdministrador(id,dadosEdicao);
        return ResponseEntity.status(HttpStatus.OK).body(atualizar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        administradorGeralService.excluir(id,idSolicitante);
        return ResponseEntity.noContent().build();
    }
}

