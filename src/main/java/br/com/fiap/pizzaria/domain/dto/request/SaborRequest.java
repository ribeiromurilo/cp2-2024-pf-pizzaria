package br.com.fiap.pizzaria.domain.dto.request;

import jakarta.validation.constraints.NotNull;

public record SaborRequest(
        @NotNull(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "A descrição é obrigatória")
        String descricao
) {
}