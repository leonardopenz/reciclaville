package br.com.senai.reciclaville.models.DTOS.clients;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDTO {

    @NotBlank
    private String nome;
    @NotBlank
    private String cnpj;
    private String atividadeEconomica;
    private String responsavel;
}
