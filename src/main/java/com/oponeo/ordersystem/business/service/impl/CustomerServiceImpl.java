package com.oponeo.ordersystem.business.service.impl;

import com.oponeo.ordersystem.business.domain.Customer;
import com.oponeo.ordersystem.business.service.CustomerService;
import com.oponeo.ordersystem.database.repository.CustomerRepository;
import com.oponeo.ordersystem.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new NotFoundException("Could not find customer by id: [%s]".formatted(customerId)));
    }
}