package br.com.psicossocial.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_participacao", uniqueConstraints = {@UniqueConstraint(
        name = "uk_participante_aplicacao",
        columnNames = {"participante_id", "aplicacao_id"})})
@Getter
@Setter
public class Participacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime dataInicio;

    private LocalDateTime dataTermino;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusParticipacao status;

    @ManyToOne
    @JoinColumn(name = "participante_id", nullable = false)
    private Participante participante;

    @ManyToOne
    @JoinColumn(name = "aplicacao_id", nullable = false)
    private Aplicacao aplicacao;
}