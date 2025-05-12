package br.com.senai.reciclaville.models.DTOS.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsRequestDTO {
    private Long declaracao_id;
    private Long material_id;
}
