package br.com.fiap.pizzaria.domain.dto.request;

import java.math.BigDecimal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record OpcionalRequest(
        @NotNull(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Preço é obrigatório")
        BigDecimal preco,

        @Valid
        AbstractRequest sabor
) {
}