package com.oponeo.ordersystem.api.dto.response;

import lombok.Builder;

@Builder
public record CustomerResponseDto(

        Long id,
        String email,
        String name,
        String phone,
        String addressLine,
        String city,
        String postalCode,
        String country
) {
}
