package com.oponeo.ordersystem.api.controller;

import com.oponeo.ordersystem.api.dto.request.CustomerRequestDto;
import com.oponeo.ordersystem.api.dto.response.CustomerResponseDto;
import com.oponeo.ordersystem.api.dto.mapper.CustomerDtoMapper;
import com.oponeo.ordersystem.business.domain.Customer;
import com.oponeo.ordersystem.business.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CustomerController.API_CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {

    public static final String API_CUSTOMER = "/customers";
    public static final String API_CUSTOMER_ID = "/{customerId}";

    private final CustomerService customerService;
    private final CustomerDtoMapper customerDtoMapper;

    @PostMapping
    public ResponseEntity<CustomerResponseDto> registerCustomer(
            @Valid @RequestBody CustomerRequestDto customerRequestDto
    ) {
        Customer savedCustomer = customerService
                .registerCustomer(customerDtoMapper.mapFromRequestDto(customerRequestDto));
        CustomerResponseDto customerResponseDto = customerDtoMapper.mapToResponseDto(savedCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponseDto);
    }

    @GetMapping(value = API_CUSTOMER_ID)
    public CustomerResponseDto getCustomerById(@PathVariable Long customerId) {
        return customerDtoMapper.mapToResponseDto(customerService.getCustomerById(customerId));
    }
}
