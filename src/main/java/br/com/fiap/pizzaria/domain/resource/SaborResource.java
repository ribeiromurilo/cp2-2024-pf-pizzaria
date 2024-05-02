package br.com.fiap.pizzaria.domain.resource;

import br.com.fiap.pizzaria.domain.dto.request.SaborRequest;
import br.com.fiap.pizzaria.domain.dto.response.SaborResponse;
import br.com.fiap.pizzaria.domain.service.SaborService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/sabores")
public class SaborResource {

    @Autowired
    private SaborService saborService;

    @GetMapping
    public ResponseEntity<Collection<SaborResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "descricao", required = false) String descricao
    ) {
        var encontrados = saborService.findAll();
        var resposta = encontrados.stream().map(saborService::toResponse).toList();
        return ResponseEntity.ok(resposta);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SaborResponse> findById(@PathVariable Long id) {
        var encontrado = saborService.findById(id);
        if (Objects.isNull(encontrado))
            return ResponseEntity.notFound().build();
        var resposta = saborService.toResponse(encontrado);
        return ResponseEntity.ok(resposta);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<SaborResponse> save(@RequestBody @Valid SaborRequest saborRequest) {
        var entity = saborService.toEntity(saborRequest);
        var saved = saborService.save(entity);
        var resposta = saborService.toResponse(saved);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(uri).body(resposta);
    }
}
