package br.com.senai.reciclaville.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Clients {

    private Long id;

    private String nome;

    private String cnpj;

    private String atividadeEconomica;

    private String responsavel;

}
