package com.oponeo.ordersystem.util;

import com.oponeo.ordersystem.business.domain.Customer;
import com.oponeo.ordersystem.business.domain.OrderItem;
import com.oponeo.ordersystem.business.domain.Product;

import java.math.BigDecimal;

public class DomainFixtures {

    public static Product someProduct() {
        return Product.builder()
                .id(1L)
                .name("Test Product")
                .netPrice(new BigDecimal("100.00"))
                .vatPercent(new BigDecimal("23.00"))
                .build();
    }

    public static Customer someCustomer() {
        return Customer.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .phone("123456789")
                .addressLine("Baker Street 221b")
                .city("London")
                .postalCode("00-000")
                .country("England")
                .build();
    }

    public static OrderItem someOrderItem() {
        return OrderItem.builder()
                .product(someProduct())
                .quantity(2)
                .netValue(new BigDecimal("200.00"))
                .grossValue(new BigDecimal("246.00"))
                .build();
    }
}
