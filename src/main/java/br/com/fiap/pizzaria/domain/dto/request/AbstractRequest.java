package br.com.fiap.pizzaria.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AbstractRequest(
        @NotNull(message = "O id é obrigatório")
        @Positive(message = "O id deve ser positivo")
        Long id
) {
}