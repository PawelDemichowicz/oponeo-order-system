package com.oponeo.ordersystem.business.domain;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Value
@With
@Builder(toBuilder = true)
public class Order {

    Long id;
    String orderNumber;
    LocalDateTime createdAt;
    BigDecimal netValue;
    BigDecimal grossValue;
    Customer customer;

    @Singular("item")
    Set<OrderItem> orderItems;
}
