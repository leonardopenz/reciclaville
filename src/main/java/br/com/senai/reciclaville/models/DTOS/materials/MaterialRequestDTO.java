package br.com.senai.reciclaville.models.DTOS.materials;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @DecimalMin(value = "0.0", inclusive = false, message = "O percentual deve ser maior que 0")
    private BigDecimal percentualCompensacao;
}
