
package br.com.psicossocial.controller;

import br.com.psicossocial.dto.ParticipanteCadastroDTO;
import br.com.psicossocial.dto.ParticipanteEdicaoDTO;
import br.com.psicossocial.dto.ParticipanteResponseDTO;
import br.com.psicossocial.service.ParticipanteService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    private final ParticipanteService participanteService;

    public ParticipanteController(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    // Cadastra um novo participante.
    @PostMapping
    public ResponseEntity<ParticipanteResponseDTO> cadastrar(
            @RequestBody @Valid ParticipanteCadastroDTO dados) {

        ParticipanteResponseDTO novoParticipante =
                participanteService.cadastrar(dados);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novoParticipante);
    }

    // Lista todos os participantes.
    @GetMapping
    public ResponseEntity<List<ParticipanteResponseDTO>> listarTodos() {

        return ResponseEntity.ok(participanteService.listarTodos());
    }

    // Busca um participante pelo ID.
    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteResponseDTO> buscarPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(participanteService.buscarPorId(id));
    }

    // Atualiza os dados de um participante.
    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteResponseDTO> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid ParticipanteEdicaoDTO dados) {

        ParticipanteResponseDTO atualizado =
                participanteService.atualizar(id, dados);

        return ResponseEntity.ok(atualizado);
    }

    // Exclui um participante.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {

        participanteService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
