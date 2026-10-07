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
 * de negócio para {@link AdministradorService}. Os DTOs evitam que as entidades
 * do domínio sejam expostas diretamente pela API.
 * </p>
 */
@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    /** Serviço que centraliza as regras de negócio dos administradores. */
    private final AdministradorService administradorService;

    /**
     * Injeta o serviço utilizado pelos endpoints deste controller.
     *
     * @param administradorService serviço de administradores
     */
    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    /**
     * Cria um novo administrador.
     * <p>
     * O corpo da requisição é validado antes de o cadastro ser encaminhado ao
     * serviço. Em caso de sucesso, a API devolve os dados criados com status 201.
     * </p>
     *
     * @param dadosCadastro dados necessários para cadastrar o administrador
     * @return o administrador cadastrado e o status HTTP {@code 201 Created}
     */
    @PostMapping
    public ResponseEntity<AdministradorResponseDTO> cadastrar(
            @RequestBody @Valid AdministradorCadastroDTO dadosCadastro){
        AdministradorResponseDTO administrador = administradorService.cadastrar(dadosCadastro);
        return ResponseEntity.status(HttpStatus.CREATED).body(administrador);
    }

    /**
     * Busca um administrador pelo seu identificador.
     *
     * @param id identificador do administrador informado na URL
     * @return os dados do administrador encontrado e o status HTTP {@code 200 OK}
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdministradorResponseDTO> buscarPorId(@PathVariable Integer id){
        AdministradorResponseDTO administrador = administradorService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(administrador);
    }

    /**
     * Atualiza os dados de um administrador existente.
     * <p>
     * O identificador define qual administrador será alterado, enquanto o corpo
     * da requisição contém os novos dados, validados antes da atualização.
     * </p>
     *
     * @param id identificador do administrador a ser atualizado
     * @param dadosEdicao dados que devem ser atualizados
     * @return o administrador atualizado e o status HTTP {@code 200 OK}
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdministradorResponseDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid AdministradorEdicaoDTO dadosEdicao){
        AdministradorResponseDTO administrador = administradorService.atualizarAdministrador(id,dadosEdicao);
        return ResponseEntity.status(HttpStatus.OK).body(administrador);
    }
}
