
    package br.com.psicossocial.controller;

    import br.com.psicossocial.dto.ParticipacaoResponseDTO;
    import br.com.psicossocial.dto.RespostaCadastroDTO;
    import br.com.psicossocial.dto.RespostaResponseDTO;
    import br.com.psicossocial.service.ParticipacaoService;
    import jakarta.validation.Valid;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    /**
     * Controller responsável pelas operações de participação
     * e respostas dos questionários.
     *
     * Os DTOs evitam expor diretamente as entidades do banco.
     */
    @RestController
    @RequestMapping("/participacoes")
    public class ParticipacaoController {

        private final ParticipacaoService participacaoService;

        public ParticipacaoController(
                ParticipacaoService participacaoService) {
            this.participacaoService = participacaoService;
        }

        // Inicia ou recupera uma participação
        @PostMapping("/iniciar")
        public ResponseEntity<ParticipacaoResponseDTO> iniciarOuRecuperar(
                @RequestParam Integer participanteId,
                @RequestParam Integer aplicacaoId) {

            ParticipacaoResponseDTO participacao =
                    participacaoService.iniciarOuRecuperarParticipacao(
                            participanteId,
                            aplicacaoId
                    );

            return ResponseEntity.status(HttpStatus.OK).body(participacao);
        }

        // Salva ou atualiza uma resposta
        @PostMapping("/{participacaoId}/respostas")
        public ResponseEntity<RespostaResponseDTO> salvarResposta(
                @PathVariable Integer participacaoId,
                @RequestBody @Valid RespostaCadastroDTO dados) {

            RespostaResponseDTO resposta =
                    participacaoService.salvarResposta(
                            participacaoId,
                            dados
                    );

            return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
        }

        // Consulta as respostas de uma participação
        @GetMapping("/{participacaoId}/respostas")
        public ResponseEntity<List<RespostaResponseDTO>> buscarRespostas(
                @PathVariable Integer participacaoId) {

            List<RespostaResponseDTO> respostas =
                    participacaoService.buscarRespostasDaParticipacao(
                            participacaoId
                    );

            return ResponseEntity.status(HttpStatus.OK).body(respostas);
        }

        // Finaliza uma participação.
        @PutMapping("/{participacaoId}/finalizar")
        public ResponseEntity<ParticipacaoResponseDTO> finalizarParticipacao(
                @PathVariable Integer participacaoId) {

            ParticipacaoResponseDTO participacao =
                    participacaoService.finalizarParticipacao(participacaoId);

            return ResponseEntity.ok(participacao);
        }
    }
