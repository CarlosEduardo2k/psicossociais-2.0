package br.com.psicossocial.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tb_bloco")
@Getter
@Setter
public class Bloco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 150)
    private String nome;

    @Column(nullable = false)
    private Integer ordem;

    @ManyToOne
    @JoinColumn(name="questionario_id",nullable = false)
    private Questionario questionario;
}
