package br.com.senai.reciclaville.models.DTOS.statements;

import br.com.senai.reciclaville.models.DTOS.items.ItemsResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementsResponseDTO {
    private Long id;
    private String cliente;
    private LocalDate dataDeclaracao;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private Double totalMateriais;
    private Double totalCompensacao;
    private List<ItemsResponseDTO> itensDeclaracao;
}
