package br.com.fiap.pizzaria.domain.resource;

import br.com.fiap.pizzaria.domain.dto.request.OpcionalRequest;
import br.com.fiap.pizzaria.domain.dto.response.OpcionalResponse;
import br.com.fiap.pizzaria.domain.service.OpcionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping(value = "/opcionais")
public class OpcionalResource implements ResourceDTO<OpcionalRequest, OpcionalResponse> {
    @Autowired
    private OpcionalService service;

    @GetMapping
    @Override
    public ResponseEntity<Collection<OpcionalResponse>> findAll() {
        Collection<OpcionalResponse> opcionais = service.findAll().stream()
                .map(service::toResponse)
                .toList();
        return ResponseEntity.ok(opcionais);
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<OpcionalResponse> findById(@PathVariable Long id) {
        var opcional = service.findById(id);
        if (opcional == null) {
            return ResponseEntity.notFound().build();
        } else {
            var response = service.toResponse(opcional);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping
    @Override
    public ResponseEntity<OpcionalResponse> save(@RequestBody @Valid OpcionalRequest r) {
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
