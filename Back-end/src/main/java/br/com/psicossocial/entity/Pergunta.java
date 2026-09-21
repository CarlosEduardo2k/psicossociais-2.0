package br.com.psicossocial.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tb_pergunta")
@Getter
@Setter
public class Pergunta {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String texto;

    @Column(nullable = false)
    private Integer ordem;

    @ManyToOne
    @JoinColumn(name="bloco_id",nullable = false)
    private Bloco bloco;

    @ManyToOne
    @JoinColumn(name="categoria_id",nullable = false)
    private Categoria categoria;
}
