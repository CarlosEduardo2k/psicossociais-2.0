
package br.com.psicossocial.controller;

import br.com.psicossocial.entity.Participante;
import br.com.psicossocial.service.ParticipanteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    private final ParticipanteService participanteService;

    // Injeta o serviço responsável pelas operações dos participantes.
    public ParticipanteController(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    // Cadastra um novo participante.
    @PostMapping
    public ResponseEntity<Participante> cadastrar(
            @RequestBody Participante participante) {

        Participante novoParticipante =
                participanteService.cadastrar(participante);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novoParticipante);
    }

    // Lista todos os participantes.
    @GetMapping
    public ResponseEntity<List<Participante>> listarTodos() {

        return ResponseEntity.ok(participanteService.listarTodos());
    }

    // Busca um participante pelo ID.
    @GetMapping("/{id}")
    public ResponseEntity<Participante> buscarPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(participanteService.buscarPorId(id));
    }

    // Atualiza os dados de um participante.
    @PutMapping("/{id}")
    public ResponseEntity<Participante> atualizar(
            @PathVariable Integer id,
            @RequestBody Participante participante) {

        Participante atualizado =
                participanteService.atualizar(id, participante);

        return ResponseEntity.ok(atualizado);
    }

    // Exclui um participante.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {

        participanteService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
