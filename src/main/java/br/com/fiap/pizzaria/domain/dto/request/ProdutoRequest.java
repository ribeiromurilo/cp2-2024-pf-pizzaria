package br.com.fiap.pizzaria.domain.dto.request;

import java.math.BigDecimal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProdutoRequest(
        @NotNull(message = "O nome é obrigatório")
        String nome,

        @NotNull(message = "O preco é obrigatório")
        BigDecimal preco,

        @Valid
        @NotNull(message = "O sabor é obrigatório")
        SaborRequest sabor,

        @Valid
        List<OpcionalRequest> opcionais
) {
}
