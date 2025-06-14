package com.oponeo.ordersystem.api.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponseDto(

        String orderNumber,
        LocalDateTime createdAt,
        BigDecimal netValue,
        BigDecimal grossValue,
        CustomerResponseDto customer,
        List<OrderItemResponseDto> orderItems
) {
}
