package br.com.psicossocial.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name="tb_aplicacao")
@Getter
@Setter
public class Aplicacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime dataInicio;

    @Column(nullable = false)
    private LocalDateTime dataTermino;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAplicacao status;

    @ManyToOne
    @JoinColumn(name="empresa_id",nullable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name="questionario_id")
    private Questionario questionario;
}
