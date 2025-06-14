package com.oponeo.ordersystem.api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductRequestDto(

        @NotBlank
        @Size(max = 150)
        String name,

        @DecimalMin("0.0")
        @Digits(integer = 8, fraction = 2)
        BigDecimal netPrice,

        @DecimalMin("0.0")
        @Digits(integer = 3, fraction = 2)
        BigDecimal vatPercent
) {
}
