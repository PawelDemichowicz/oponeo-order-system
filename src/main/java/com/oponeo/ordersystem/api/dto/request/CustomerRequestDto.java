package com.oponeo.ordersystem.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CustomerRequestDto(

        @Email
        @NotBlank
        @Size(max = 100)
        String email,

        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Pattern(regexp = "\\+?[0-9\\-\\s]{7,15}")
        @Size(max = 30)
        String phone,

        @NotBlank
        @Size(max = 200)
        String addressLine,

        @NotBlank
        @Size(max = 100)
        String city,

        @NotBlank
        @Size(max = 20)
        String postalCode,

        @NotBlank
        @Size(max = 100)
        String country
) {
}
