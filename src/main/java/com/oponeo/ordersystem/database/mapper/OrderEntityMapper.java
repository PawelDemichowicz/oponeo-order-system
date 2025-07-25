package com.oponeo.ordersystem.database.mapper;

import com.oponeo.ordersystem.business.domain.Order;
import com.oponeo.ordersystem.database.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = {OrderItemEntityMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderEntityMapper {

    Order mapFromEntity(OrderEntity entity);

    OrderEntity mapToEntity(Order domain);
}
