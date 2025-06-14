package com.oponeo.ordersystem.api.dto.mapper;

import com.oponeo.ordersystem.api.dto.request.OrderRequestDto;
import com.oponeo.ordersystem.api.dto.response.OrderResponseDto;
import com.oponeo.ordersystem.business.domain.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = {CustomerDtoMapper.class, OrderItemDtoMapper.class},
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface OrderDtoMapper {

    @Mapping(source = "customerId", target = "customer.id")
    Order mapFromRequestDto(OrderRequestDto dto);

    OrderResponseDto mapToResponseDto(Order order);
}