package br.com.fiap.pizzaria.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sabor {
    private Long id;
    private String nome;
    private String descricao;
}