package com.oponeo.ordersystem.integration.support;

import com.oponeo.ordersystem.api.controller.CustomerController;
import com.oponeo.ordersystem.api.dto.request.CustomerRequestDto;
import com.oponeo.ordersystem.api.dto.response.CustomerResponseDto;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface CustomerControllerTestSupport {

    RequestSpecification requestSpecification();

    default CustomerResponseDto registerCustomer(CustomerRequestDto customerRequestDto) {
        return requestSpecification()
                .body(customerRequestDto)
                .post(CustomerController.API_CUSTOMER)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CustomerResponseDto.class);
    }

    default CustomerResponseDto getCustomerById(Long customerId) {
        return requestSpecification()
                .get(CustomerController.API_CUSTOMER + "/" + customerId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CustomerResponseDto.class);
    }
}
