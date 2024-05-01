package br.com.fiap.pizzaria.domain.entity;

import jakarta.persistence.*;
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

@Entity
@Table(name = "TB_PRODUTO")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PRODUTO")
    @SequenceGenerator(name = "SQ_PRODUTO", sequenceName = "SQ_PRODUTO", allocationSize =  1)
    @Column(name = "ID_PRODUTO")
    private Long id;

    @Column(name = "NM_PRODUTO")
    private String nome;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "ID_SABOR",
            referencedColumnName = "ID_SABOR",
            foreignKey = @ForeignKey(
                    name = "FK_PRODUTO_SABOR"
            )
    )
    private Sabor sabor;

    @Column(name = "PRECO_PRODUTO")
    private BigDecimal preco;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "TB_PRODUTO_OPCIONAIS",
            joinColumns = {
                @JoinColumn(
                        name = "ID_PRODUTO",
                        referencedColumnName = "ID_PRODUTO",
                        foreignKey = @ForeignKey(
                                name = "FK_PRODUTO_OPCIONAIS"
                        )
                )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "ID_OPCIONAL",
                            referencedColumnName = "ID_OPCIONAL",
                            foreignKey = @ForeignKey(
                                    name = "FK_OPCIONAIS_PRODUTO"
                            )
                    )
            }
    )
    private Set<Opcional> opcionais = new LinkedHashSet<>();
}
