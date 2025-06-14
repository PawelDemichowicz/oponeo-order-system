package com.oponeo.ordersystem.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderRequestDto(

        @NotNull
        Long customerId,

        @NotNull
        List<OrderItemRequestDto> orderItems
) {
}
