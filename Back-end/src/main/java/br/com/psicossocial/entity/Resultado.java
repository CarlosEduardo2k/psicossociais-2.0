package br.com.psicossocial.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_resultado")
@Getter
@Setter
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(precision = 10, scale = 2)
    private BigDecimal mediaGeral;

    @OneToOne
    @JoinColumn(name = "participacao_id", nullable = false, unique = true)
    private Participacao participacao;
}