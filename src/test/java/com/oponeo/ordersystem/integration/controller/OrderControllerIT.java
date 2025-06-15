package com.oponeo.ordersystem.integration.controller;

import com.oponeo.ordersystem.api.controller.OrderController;
import com.oponeo.ordersystem.api.dto.request.OrderItemRequestDto;
import com.oponeo.ordersystem.api.dto.request.OrderRequestDto;
import com.oponeo.ordersystem.api.dto.response.CustomerResponseDto;
import com.oponeo.ordersystem.api.dto.response.OrderResponseDto;
import com.oponeo.ordersystem.api.dto.response.ProductResponseDto;
import com.oponeo.ordersystem.integration.configuration.RestAssuredIntegrationTestBase;
import com.oponeo.ordersystem.integration.support.CustomerControllerTestSupport;
import com.oponeo.ordersystem.integration.support.OrderControllerTestSupport;
import com.oponeo.ordersystem.integration.support.ProductControllerTestSupport;
import com.oponeo.ordersystem.util.DtoFixtures;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class OrderControllerIT extends RestAssuredIntegrationTestBase
        implements OrderControllerTestSupport,
        CustomerControllerTestSupport,
        ProductControllerTestSupport {

    @Test
    void shouldPlaceAndGetOrder() {
        // given
        CustomerResponseDto customer = registerCustomer(DtoFixtures.someCustomerRequest());
        ProductResponseDto product = addProduct(DtoFixtures.someProductRequest());
        OrderRequestDto orderRequest = DtoFixtures.someOrderRequest(customer.id(), product.id());

        // when
        OrderResponseDto createdOrder = placeOrder(orderRequest);
        OrderResponseDto fetchedOrder = getOrderByNumber(createdOrder.orderNumber());

        // then
        assertThat(createdOrder.orderNumber()).isNotBlank();
        assertThat(createdOrder.customer().id()).isEqualTo(customer.id());
        assertThat(fetchedOrder.orderItems()).hasSize(1);
        assertThat(fetchedOrder.grossValue()).isEqualTo(createdOrder.grossValue());
    }

    @Test
    void shouldReturn404WhenProductNotFound() {
        // given
        CustomerResponseDto customer = registerCustomer(DtoFixtures.someCustomerRequest());
        OrderRequestDto orderRequest = DtoFixtures.someOrderRequest(customer.id(), 99L);

        // when & then
        given()
                .spec(requestSpecification())
                .body(orderRequest)
                .when()
                .post(OrderController.API_ORDER)
                .then()
                .statusCode(NOT_FOUND.value());
    }

    @Test
    void shouldReturn404WhenOrderNumberNotFound() {
        // when & then
        given()
                .spec(requestSpecification())
                .when()
                .get(OrderController.API_ORDER + "/ORD-UNKNOWN-999")
                .then()
                .statusCode(NOT_FOUND.value());
    }

    @Test
    void shouldReturn400WhenOrderContainsDuplicateProducts() {
        // given
        CustomerResponseDto customer = registerCustomer(DtoFixtures.someCustomerRequest());
        ProductResponseDto product = addProduct(DtoFixtures.someProductRequest());

        OrderRequestDto duplicateOrderRequest = OrderRequestDto.builder()
                .customerId(customer.id())
                .orderItems(List.of(
                        OrderItemRequestDto.builder().productId(product.id()).quantity(1).build(),
                        OrderItemRequestDto.builder().productId(product.id()).quantity(2).build()
                ))
                .build();

        // when & then
        given()
                .spec(requestSpecification())
                .body(duplicateOrderRequest)
                .when()
                .post(OrderController.API_ORDER)
                .then()
                .statusCode(BAD_REQUEST.value());
    }
}
