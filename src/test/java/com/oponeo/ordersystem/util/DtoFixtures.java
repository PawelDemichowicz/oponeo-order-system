package com.oponeo.ordersystem.util;

import com.oponeo.ordersystem.api.dto.request.CustomerRequestDto;
import com.oponeo.ordersystem.api.dto.request.OrderItemRequestDto;
import com.oponeo.ordersystem.api.dto.request.OrderRequestDto;
import com.oponeo.ordersystem.api.dto.request.ProductRequestDto;

import java.math.BigDecimal;
import java.util.List;

public class DtoFixtures {

    public static CustomerRequestDto someCustomerRequest() {
        return CustomerRequestDto.builder()
                .email("john.doe@example.com")
                .name("John Doe")
                .phone("+48123456789")
                .addressLine("Baker Street 221b")
                .city("London")
                .postalCode("00-000")
                .country("England")
                .build();
    }

    public static ProductRequestDto someProductRequest() {
        return ProductRequestDto.builder()
                .name("Test Product")
                .netPrice(new BigDecimal("100.00"))
                .vatPercent(new BigDecimal("23.00"))
                .build();
    }

    public static OrderRequestDto someOrderRequest(Long customerId, Long productId) {
        return OrderRequestDto.builder()
                .customerId(customerId)
                .orderItems(List.of(
                        OrderItemRequestDto.builder()
                                .productId(productId)
                                .quantity(2)
                                .build()
                ))
                .build();
    }
}
