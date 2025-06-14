package com.oponeo.ordersystem.api.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemResponseDto(

        Integer quantity,
        BigDecimal netValue,
        BigDecimal grossValue,
        String productName,
        BigDecimal netPrice,
        BigDecimal vatPercent
) {
}
