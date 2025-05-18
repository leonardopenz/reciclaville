package br.com.senai.reciclaville.models.DTOS.items;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsRequestDTO {
    @NotNull
    private Long material_id;
    private Double toneladasDeclaradas;
}
