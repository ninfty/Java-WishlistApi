package com.community.wishlist.service;

import com.community.wishlist.exception.EntityAlreadyExistsException;
import com.community.wishlist.exception.ResourceNotFoundException;
import com.community.wishlist.model.Customer;
import com.community.wishlist.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(Customer customer) throws EntityAlreadyExistsException {
        boolean userAlreadyExists = this.findByEmail(customer.getEmail()).isPresent();

        if (userAlreadyExists) {
            throw new EntityAlreadyExistsException("User already exists.");
        }

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

        Customer customer = optionalCustomer.orElseThrow(() -> new ResourceNotFoundException(""));

        customer.setEmail(newCustomer.getEmail());
        customer.setName(newCustomer.getName());

        return customer;
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Customer> optionalCustomer = this.findById(id);

        Customer customer = optionalCustomer.orElseThrow(() -> new ResourceNotFoundException(""));

        this.customerRepository.delete(customer);
    }

    public Optional<Customer> findByEmail(String email) {
        return this.customerRepository.findByEmail(email);
    }
}
