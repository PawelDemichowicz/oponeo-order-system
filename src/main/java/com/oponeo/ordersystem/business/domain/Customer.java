package com.oponeo.ordersystem.business.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@Builder(toBuilder = true)
public class Customer {

    Long id;
    String email;
    String name;
    String phone;
    String addressLine;
    String city;
    String postalCode;
    String country;
}