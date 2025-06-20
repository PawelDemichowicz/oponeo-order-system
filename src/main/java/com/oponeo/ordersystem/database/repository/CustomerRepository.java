package com.oponeo.ordersystem.database.repository;

import com.oponeo.ordersystem.business.domain.Customer;
import com.oponeo.ordersystem.database.entity.CustomerEntity;
import com.oponeo.ordersystem.database.mapper.CustomerEntityMapper;
import com.oponeo.ordersystem.database.repository.jpa.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerEntityMapper customerEntityMapper;

    public Customer save(Customer customer) {
        CustomerEntity customerToSave = customerEntityMapper.mapToEntity(customer);
        return customerEntityMapper.mapFromEntity(customerJpaRepository.save(customerToSave));
    }

    public Optional<Customer> findById(Long customerId) {
        return customerJpaRepository.findById(customerId)
                .map(customerEntityMapper::mapFromEntity);
    }

    public void deleteAll() {
        customerJpaRepository.deleteAll();
    }
}
