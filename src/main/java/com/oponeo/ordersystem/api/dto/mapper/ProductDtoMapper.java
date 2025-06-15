package com.oponeo.ordersystem.api.dto.mapper;

import com.oponeo.ordersystem.api.dto.request.ProductRequestDto;
import com.oponeo.ordersystem.api.dto.response.ProductResponseDto;
import com.oponeo.ordersystem.business.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductDtoMapper {

    Product mapFromRequestDto(ProductRequestDto dto);

    ProductResponseDto mapToResponseDto(Product domain);
}
