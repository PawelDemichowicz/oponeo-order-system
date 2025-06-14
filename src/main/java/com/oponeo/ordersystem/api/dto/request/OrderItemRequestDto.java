package com.oponeo.ordersystem.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OrderItemRequestDto(

        @NotNull
        Long productId,

        @Min(1)
        Integer quantity
) {
}
