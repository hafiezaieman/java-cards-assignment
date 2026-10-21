package com.hafiez.cards_assignment.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import com.hafiez.cards_assignment.dto.*;
import com.hafiez.cards_assignment.entity.Product;
import com.hafiez.cards_assignment.repository.ProductRepository;
import com.hafiez.cards_assignment.exception.ResourceNotFoundException;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository repository;
    private static final int PAGE_SIZE = 10;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional                       // INSERT
    public ProductResponse create(ProductRequest req) {
        Product p = new Product();
        apply(p, req);
        return toResponse(repository.save(p));
    }

    @Transactional(readOnly = true)      // GET by ID
    public ProductResponse getById(Long id) {
        return toResponse(find(id));
    }

    @Transactional(readOnly = true)     // GET by Page
    public PagedResponse<Product> getProducts(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("id").ascending());
        Page<Product> result = repository.findAll(pageable);

        return new PagedResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );
    }

    @Transactional                       // UPDATE
    public ProductResponse update(Long id, ProductRequest req) {
        Product p = find(id);
        apply(p, req);
        return toResponse(repository.save(p));
    }

    @Transactional                      // DELETE
    public void delete(Long id) {
        repository.delete(find(id));
    }

    private Product find(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    private void apply(Product p, ProductRequest r) {
        p.setName(r.getName());
        p.setDescription(r.getDescription());
        p.setPrice(r.getPrice());
        p.setQuantity(r.getQuantity());
    }

    private ProductResponse toResponse(Product p) {
        ProductResponse res = new ProductResponse();
        res.setId(p.getId());
        res.setName(p.getName());
        res.setDescription(p.getDescription());
        res.setPrice(p.getPrice());
        res.setQuantity(p.getQuantity());
        res.setCreatedAt(p.getCreatedAt());
        return res;
    }
}