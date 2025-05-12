package br.com.senai.reciclaville.models.DTOS.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsResponseDTO {
    private Long id;
    private Long cliente_id;
    private Long material_id;
    private Double percentualCompensacao;
    private Double toneladasDeclaradas;
    private Double toneladasCompensadas;
}
