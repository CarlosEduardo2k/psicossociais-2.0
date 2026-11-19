package br.com.psicossocial.controller;


import br.com.psicossocial.entity.Categoria;
import br.com.psicossocial.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<Categoria> cadastrar(@RequestBody Categoria categoria) {
        categoriaService.cadastrarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }
}
