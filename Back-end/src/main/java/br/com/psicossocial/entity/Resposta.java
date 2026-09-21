package br.com.psicossocial.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_resposta", uniqueConstraints =
        {@UniqueConstraint(name = "uk_participacao_pergunta", columnNames = {
                "participacao_id", "pergunta_id"})})
@Getter
@Setter
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer valor;

    @ManyToOne
    @JoinColumn(name = "participacao_id", nullable = false)
    private Participacao participacao;

    @ManyToOne
    @JoinColumn(name = "pergunta_id", nullable = false)
    private Pergunta pergunta;
}