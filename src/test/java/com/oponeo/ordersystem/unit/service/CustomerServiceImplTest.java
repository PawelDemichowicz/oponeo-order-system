package com.oponeo.ordersystem.unit.service;

import com.oponeo.ordersystem.business.domain.Customer;
import com.oponeo.ordersystem.business.service.impl.CustomerServiceImpl;
import com.oponeo.ordersystem.database.repository.CustomerRepository;
import com.oponeo.ordersystem.exception.NotFoundException;
import com.oponeo.ordersystem.util.DomainFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;

    @BeforeEach
    void setup() {
        customer = DomainFixtures.someCustomer();
    }

    @Test
    void shouldRegisterCustomerSuccessfully() {
        // given
        when(customerRepository.save(customer)).thenReturn(customer);

        // when
        Customer result = customerService.registerCustomer(customer);

        // then
        assertNotNull(result);
        assertEquals(customer, result);
    }

    @Test
    void shouldReturnCustomerById() {
        // given
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        // when
        Customer result = customerService.getCustomerById(customer.getId());

        // then
        assertNotNull(result);
        assertEquals(customer, result);
    }

    @Test
    void shouldThrowWhenCustomerNotFound() {
        // given
        Long missingId = 99L;
        when(customerRepository.findById(missingId)).thenReturn(Optional.empty());

        // when & then
        Throwable exception = assertThrows(NotFoundException.class, () -> customerService.getCustomerById(missingId));
        assertEquals("Could not find customer by id: [%s]".formatted(missingId), exception.getMessage());
    }
}
