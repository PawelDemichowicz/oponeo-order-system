package com.oponeo.ordersystem.database.mapper;

import com.oponeo.ordersystem.business.domain.Product;
import com.oponeo.ordersystem.database.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ProductEntityMapper {

    Product mapFromEntity(ProductEntity entity);

    ProductEntity mapToEntity(Product domain);
}
