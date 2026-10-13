package br.com.psicossocial.controller;

import br.com.psicossocial.dto.AdministradorCadastroDTO;
import br.com.psicossocial.dto.AdministradorEdicaoDTO;
import br.com.psicossocial.dto.AdministradorResponseDTO;
import br.com.psicossocial.service.AdministradorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Expõe os endpoints HTTP responsáveis pelo ciclo de vida dos administradores.
 * <p>
 * A classe recebe as requisições, valida os dados de entrada e delega as regras
 * de negócio. Os DTOs evitam que as entidades do domínio sejam expostas diretamente pela API.
 * </p>
 */
@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @PostMapping
    public ResponseEntity<AdministradorResponseDTO> cadastrar(@RequestBody @Valid AdministradorCadastroDTO dadosCadastro){
        AdministradorResponseDTO administrador = administradorService.cadastrar(dadosCadastro);
        return ResponseEntity.status(HttpStatus.CREATED).body(administrador);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministradorResponseDTO> buscarPorId(@PathVariable Integer id){
        AdministradorResponseDTO administrador = administradorService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(administrador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdministradorResponseDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid AdministradorEdicaoDTO dadosEdicao){
        AdministradorResponseDTO administrador = administradorService.atualizarAdministrador(id,dadosEdicao);
        return ResponseEntity.status(HttpStatus.OK).body(administrador);
    }
}
