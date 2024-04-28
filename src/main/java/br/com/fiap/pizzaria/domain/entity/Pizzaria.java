package br.com.fiap.pizzaria.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Pizzaria {

    private Long id;

    private String nome;

    private Set<Produto> cardapio = new LinkedHashSet<>();

}
