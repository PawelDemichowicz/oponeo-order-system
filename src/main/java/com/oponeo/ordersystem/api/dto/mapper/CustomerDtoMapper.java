package com.oponeo.ordersystem.api.dto.mapper;

import com.oponeo.ordersystem.api.dto.request.CustomerRequestDto;
import com.oponeo.ordersystem.api.dto.response.CustomerResponseDto;
import com.oponeo.ordersystem.business.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerDtoMapper {

    Customer mapFromRequestDto(CustomerRequestDto dto);

    CustomerResponseDto mapToResponseDto(Customer domain);
}
