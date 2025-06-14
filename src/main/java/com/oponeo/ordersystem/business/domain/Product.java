package com.oponeo.ordersystem.business.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;

@Value
@With
@Builder(toBuilder = true)
public class Product {

    Long id;
    String name;
    BigDecimal netPrice;
    BigDecimal vatPercent;
}
