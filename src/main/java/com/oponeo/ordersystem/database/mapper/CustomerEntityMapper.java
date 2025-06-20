package com.oponeo.ordersystem.database.mapper;

import com.oponeo.ordersystem.business.domain.Customer;
import com.oponeo.ordersystem.database.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {

    Customer mapFromEntity(CustomerEntity entity);

    CustomerEntity mapToEntity(Customer domain);
}
