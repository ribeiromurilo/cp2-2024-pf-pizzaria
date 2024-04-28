package br.com.fiap.pizzaria.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    private Long id;
    private String nome;
    private Sabor sabor;
    private BigDecimal preco;
    private Set<Opcional> opcionais = new LinkedHashSet<>();
}
