package br.com.fiap.pizzaria.domain.resource;

import br.com.fiap.pizzaria.domain.dto.request.PizzariaRequest;
import br.com.fiap.pizzaria.domain.dto.request.ProdutoRequest;
import br.com.fiap.pizzaria.domain.dto.response.PizzariaResponse;
import br.com.fiap.pizzaria.domain.dto.response.ProdutoResponse;
import br.com.fiap.pizzaria.domain.entity.Pizzaria;
import br.com.fiap.pizzaria.domain.entity.Produto;
import br.com.fiap.pizzaria.domain.service.PizzariaService;
import br.com.fiap.pizzaria.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/pizzarias")
public class PizzariaResource implements ResourceDTO<PizzariaRequest, PizzariaResponse> {

    @Autowired
    private PizzariaService service;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    @Override
    public ResponseEntity<Collection<PizzariaResponse>> findAll() {
        Collection<Pizzaria> pizzarias = service.findAll();
        Collection<PizzariaResponse> pizzariaResponses = pizzarias.stream()
                .map(service::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pizzariaResponses);
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<PizzariaResponse> findById(@PathVariable Long id) {
        var pizzaria = service.findById(id);

        if (pizzaria == null) {
            return ResponseEntity.notFound().build();
        } else {
            var response = service.toResponse(pizzaria);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping
    @Override
    public ResponseEntity<PizzariaResponse> save(@RequestBody PizzariaRequest r) {
        var entity = service.toEntity(r);
        service.save(entity);
        var response = service.toResponse(entity);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/cardapio")
    public ResponseEntity<PizzariaResponse> addToCardapio(@PathVariable Long id, @RequestBody ProdutoRequest produtoRequest) {
        Pizzaria pizzaria = service.findById(id);

        if (pizzaria == null) {
            return ResponseEntity.notFound().build();
        } else {
            Produto produto = produtoService.toEntity(produtoRequest);
            pizzaria.getCardapio().add(produto);
            service.save(pizzaria);
            PizzariaResponse response = service.toResponse(pizzaria);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/{id}/cardapio")
    public ResponseEntity<Collection<ProdutoResponse>> getCardapio(@PathVariable Long id) {
        Pizzaria pizzaria = service.findById(id);

        if (pizzaria == null) {
            return ResponseEntity.notFound().build();
        } else {
            Collection<ProdutoResponse> cardapio = pizzaria.getCardapio().stream()
                    .map(produtoService::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(cardapio);
        }
    }
}
