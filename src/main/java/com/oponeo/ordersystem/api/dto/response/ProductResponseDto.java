package com.oponeo.ordersystem.api.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponseDto(

        Long id,
        String name,
        BigDecimal netPrice,
        BigDecimal vatPercent
) {
}
