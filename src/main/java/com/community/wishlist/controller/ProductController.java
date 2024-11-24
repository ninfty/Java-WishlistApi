package com.community.wishlist.controller;

import com.community.wishlist.exception.ResourceNotFoundException;
import com.community.wishlist.model.Product;
import com.community.wishlist.service.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Product> create(@RequestBody @Valid Product newProduct, UriComponentsBuilder uriBuilder) {
        Product product = productService.create(newProduct);
        URI uri = uriBuilder.path("/product/{id}").buildAndExpand(newProduct.getId()).toUri();

        return ResponseEntity.created(uri).body(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> read(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);

        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody @Valid Product newProduct) throws ResourceNotFoundException {
        Product product = productService.update(newProduct, id);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) throws ResourceNotFoundException {
        productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
