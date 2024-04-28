package br.com.fiap.pizzaria.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Opcional {

    private Long id;

    private String nome;

    private BigDecimal preco;

    private Sabor sabor;

}
