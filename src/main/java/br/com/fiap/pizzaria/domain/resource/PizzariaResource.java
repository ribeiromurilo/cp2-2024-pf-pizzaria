package br.com.fiap.pizzaria.domain.resource;

import br.com.fiap.pizzaria.domain.dto.request.PizzariaRequest;
import br.com.fiap.pizzaria.domain.dto.response.PizzariaResponse;
import br.com.fiap.pizzaria.domain.service.PizzariaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping(value = "/pizzarias")
public class PizzariaResource implements ResourceDTO<PizzariaRequest, PizzariaResponse> {
    @Autowired
    private PizzariaService service;

    @GetMapping
    @Override
    public ResponseEntity<Collection<PizzariaResponse>> findAll() {
        return null;
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

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<PizzariaResponse> save(@RequestBody @Valid PizzariaRequest r) {
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