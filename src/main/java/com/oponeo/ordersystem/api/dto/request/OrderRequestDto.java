package com.oponeo.ordersystem.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderRequestDto(

        @NotNull
        Long customerId,

        @NotNull
        @NotEmpty
        @Valid
        List<OrderItemRequestDto> orderItems
) {
}
