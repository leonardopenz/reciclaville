package br.com.senai.reciclaville.models.DTOS.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDTO {

    private Long id;
    private String nome;
    private String cnpj;
    private String atividadeEconomica;
    private String responsavel;
}
