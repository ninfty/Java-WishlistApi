package com.community.wishlist.service;

import com.community.wishlist.exception.EntityAlreadyExistsException;
import com.community.wishlist.exception.ResourceNotFoundException;
import com.community.wishlist.model.Customer;
import com.community.wishlist.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder encoder;

    public CustomerService(CustomerRepository customerRepository, BCryptPasswordEncoder encoder) {
        this.customerRepository = customerRepository;
        this.encoder = encoder;
    }

    public Customer create(Customer customer) throws EntityAlreadyExistsException {
        boolean userAlreadyExists = customerRepository.findByEmail(customer.getEmail()).isPresent();

        if (userAlreadyExists) {
            throw new EntityAlreadyExistsException("User already exists.");
        }

        customer.setPassword(hashPassword(customer.getPassword()));

        return this.customerRepository.save(customer);
    }

    public Optional<Customer> findById(Long id) {
        return this.customerRepository.findById(id);
    }

    public List<Customer> getAll() {
        return new ArrayList<>(customerRepository.findAll());
    }

    public Customer update(Customer newCustomer, Long id) throws ResourceNotFoundException {
        Optional<Customer> optionalCustomer = this.findById(id);

        Customer customer = optionalCustomer.orElseThrow(() -> new ResourceNotFoundException("Customer not found."));

        customer.setEmail(newCustomer.getEmail());
        customer.setName(newCustomer.getName());
        customer.setPassword(hashPassword(newCustomer.getPassword()));

        return customer;
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Customer> optionalCustomer = this.findById(id);

        Customer customer = optionalCustomer.orElseThrow(() -> new ResourceNotFoundException("Customer not found."));

        this.customerRepository.delete(customer);
    }

    private String hashPassword(String password) {
        return this.encoder.encode(password);
    }
}