package com.community.wishlist.controller;

import com.community.wishlist.exception.EntityAlreadyExistsException;
import com.community.wishlist.exception.ResourceNotFoundException;
import com.community.wishlist.model.Customer;
import com.community.wishlist.service.CustomerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Customer> create(@RequestBody @Valid Customer newCustomer, UriComponentsBuilder uriBuilder) throws EntityAlreadyExistsException {
        Customer customer = customerService.create(newCustomer);
        URI uri = uriBuilder.path("/customer/{id}").buildAndExpand(newCustomer.getId()).toUri();

        return ResponseEntity.created(uri).body(customer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> read(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);

        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody @Valid Customer newCustomer) throws ResourceNotFoundException {
        Customer customer = customerService.update(newCustomer, id);

        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) throws ResourceNotFoundException {
        customerService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
