package br.com.senai.reciclaville.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "itens_declaracao")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "declaracao_id", nullable = false)
    private Statements statement;

    @ManyToOne(optional = false)
    @JoinColumn(name = "material_id", nullable = false)
    private Materials materials;

    @Column(name = "percentual_compensacao", nullable = false)
    private Double percentualCompensacao;

    @Column(name = "toneladas_declaradas", nullable = false)
    private Double toneladasDeclaradas;

    @Column(name = "toneladas_compensadas")
    private Double toneladasCompensadas;
}
