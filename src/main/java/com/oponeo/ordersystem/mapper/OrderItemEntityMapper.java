package com.oponeo.ordersystem.mapper;

import com.oponeo.ordersystem.business.domain.OrderItem;
import com.oponeo.ordersystem.database.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface OrderItemEntityMapper {

    OrderItem mapFromEntity(OrderItemEntity entity);

    OrderItemEntity mapToEntity(OrderItem domain);
}
