package com.community.wishlist.controller;

import com.community.wishlist.exception.ResourceNotFoundException;
import com.community.wishlist.model.Product;
import com.community.wishlist.model.Wishlist;
import com.community.wishlist.service.WishlistService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Wishlist> create(@RequestBody @Valid Wishlist newWishlist, UriComponentsBuilder uriBuilder) {
        Wishlist wishlist = wishlistService.create(newWishlist);
        URI uri = uriBuilder.path("/wishlist/{id}").buildAndExpand(newWishlist.getId()).toUri();

        return ResponseEntity.created(uri).body(wishlist);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Wishlist> read(@PathVariable Long customerId) {
        Optional<Wishlist> wishlist = wishlistService.findByCustomerId(customerId);

        return wishlist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Wishlist> addProduct(@PathVariable Long id, @RequestBody @Valid Set<Product> newProducts) throws ResourceNotFoundException {
        Wishlist wishlist = wishlistService.addProduct(newProducts, id);

        return ResponseEntity.ok(wishlist);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id){
        wishlistService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
