package br.com.fiap.pizzaria.domain.resource;

import br.com.fiap.pizzaria.domain.dto.request.ProdutoRequest;
import br.com.fiap.pizzaria.domain.dto.response.ProdutoResponse;
import br.com.fiap.pizzaria.domain.service.ProdutoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource implements ResourceDTO<ProdutoRequest, ProdutoResponse> {
    @Autowired
    private ProdutoService service;

    @GetMapping
    @Override
    public ResponseEntity<Collection<ProdutoResponse>> findAll() {
        var produtos = service.findAll();

        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            var response = produtos.stream()
                    .map(service::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<ProdutoResponse> findById(@PathVariable Long id) {
        var produto = service.findById(id);

        if (produto == null) {
            return ResponseEntity.notFound().build();
        } else {
            var response = service.toResponse(produto);
            return ResponseEntity.ok(response);
        }
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<ProdutoResponse> save(@RequestBody @Valid ProdutoRequest r) {
        var entity = service.toEntity(r);
        service.save(entity);
        var response = service.toResponse(entity);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(entity.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
