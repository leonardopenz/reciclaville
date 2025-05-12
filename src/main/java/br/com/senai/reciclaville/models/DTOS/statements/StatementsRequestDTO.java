package br.com.senai.reciclaville.models.DTOS.statements;

import br.com.senai.reciclaville.models.entities.Items;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementsRequestDTO {
    private Long clienteId;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private List<Items> itensDeclaracao;
}
