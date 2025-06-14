package com.oponeo.ordersystem.api.dto.mapper;

import com.oponeo.ordersystem.api.dto.request.OrderItemRequestDto;
import com.oponeo.ordersystem.api.dto.response.OrderItemResponseDto;
import com.oponeo.ordersystem.business.domain.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface OrderItemDtoMapper {

    @Mapping(source = "productId", target = "product.id")
    OrderItem mapFromRequestDto(OrderItemRequestDto dto);

    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.netPrice", target = "netPrice")
    @Mapping(source = "product.vatPercent", target = "vatPercent")
    OrderItemResponseDto mapToResponseDto(OrderItem domain);
}
