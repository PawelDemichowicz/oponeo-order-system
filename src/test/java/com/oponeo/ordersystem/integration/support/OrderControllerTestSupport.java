package com.oponeo.ordersystem.integration.support;

import com.oponeo.ordersystem.api.controller.OrderController;
import com.oponeo.ordersystem.api.dto.request.OrderRequestDto;
import com.oponeo.ordersystem.api.dto.response.OrderResponseDto;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface OrderControllerTestSupport {

    RequestSpecification requestSpecification();

    default OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {
        return requestSpecification()
                .body(orderRequestDto)
                .post(OrderController.API_ORDER)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(OrderResponseDto.class);
    }

    default OrderResponseDto getOrderByNumber(String orderNumber) {
        return requestSpecification()
                .get(OrderController.API_ORDER + "/" + orderNumber)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(OrderResponseDto.class);
    }
}
