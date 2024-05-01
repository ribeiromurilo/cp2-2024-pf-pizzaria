package br.com.fiap.pizzaria.domain.service;

import br.com.fiap.pizzaria.domain.dto.request.ProdutoRequest;
import br.com.fiap.pizzaria.domain.dto.response.OpcionalResponse;
import br.com.fiap.pizzaria.domain.dto.response.ProdutoResponse;
import br.com.fiap.pizzaria.domain.dto.response.SaborResponse;
import br.com.fiap.pizzaria.domain.entity.Opcional;
import br.com.fiap.pizzaria.domain.entity.Produto;
import br.com.fiap.pizzaria.domain.entity.Sabor;
import br.com.fiap.pizzaria.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProdutoService implements ServiceDTO<Produto, ProdutoRequest, ProdutoResponse> {

    @Autowired
    private ProdutoRepository repo;

    @Autowired
    private SaborService saborService;

    @Autowired
    private OpcionalService opcionalService;

    @Override
    public Collection<Produto> findAll() {
        return repo.findAll();
    }

    @Override
    public Collection<Produto> findAll(Example<Produto> example) {
        return repo.findAll(example);
    }

    @Override
    public Produto findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Produto save(Produto produto) {
        return repo.save(produto);
    }

    @Override
    public Produto toEntity(ProdutoRequest produtoRequest) {
        Sabor sabor = saborService.toEntity(produtoRequest.sabor());

        Set<Opcional> opcionais = produtoRequest.opcionais().stream()
                .map(opcionalService::toEntity)
                .collect(Collectors.toSet());

        return Produto.builder()
                .nome(produtoRequest.nome())
                .preco(produtoRequest.preco())
                .sabor(sabor)
                .opcionais(opcionais)
                .build();
    }

    @Override
    public ProdutoResponse toResponse(Produto produto) {
        SaborResponse saborResponse = saborService.toResponse(produto.getSabor());
        Collection<OpcionalResponse> opcionaisResponse = produto.getOpcionais().stream()
                .map(opcionalService::toResponse)
                .collect(Collectors.toList());

        return ProdutoResponse.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .preco(produto.getPreco())
                .sabor(saborResponse)
                .opcionais(opcionaisResponse)
                .build();
    }
}
