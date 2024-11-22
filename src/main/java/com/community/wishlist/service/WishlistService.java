package com.community.wishlist.service;

import com.community.wishlist.exception.ResourceNotFoundException;
import com.community.wishlist.model.Product;
import com.community.wishlist.model.Wishlist;
import com.community.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist create(Wishlist wishlist) {
        return this.wishlistRepository.save(wishlist);
    }

    public Optional<Wishlist> findByCustomerId(Long customerId) {
        return this.wishlistRepository.findByCustomerId(customerId);
    }

    public Wishlist addProduct(Set<Product> newProducts, Long id) throws ResourceNotFoundException {
        Wishlist wishlist = this.wishlistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));

        wishlist.getProducts().addAll(newProducts);

        return wishlist;
    }

    public void delete(Long id) {
        Wishlist wishlist = this.wishlistRepository.getOne(id);

        this.wishlistRepository.delete(wishlist);
    }
}
