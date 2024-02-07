package com.boardcamp.api.services;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.exceptions.ConflictException;
import com.boardcamp.api.exceptions.NotFoundException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;

@Service
public class CustomerService {

    final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerModel findById(long id) {

        return customerRepository.findById(id).orElseThrow(
            () -> new NotFoundException("Customer not found by this id!"));
    }
    
    public CustomerModel save(CustomerDTO dto) {

        if (customerRepository.existsByCpf(dto.getCpf())) {
            throw new ConflictException("This customer already exists!");
        }

        CustomerModel customer = new CustomerModel(dto);
        return customerRepository.save(customer);
    }
    
}
