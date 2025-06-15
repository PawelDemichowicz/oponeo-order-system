package com.oponeo.ordersystem.integration.controller;

import com.oponeo.ordersystem.api.controller.CustomerController;
import com.oponeo.ordersystem.api.dto.request.CustomerRequestDto;
import com.oponeo.ordersystem.api.dto.response.CustomerResponseDto;
import com.oponeo.ordersystem.integration.configuration.RestAssuredIntegrationTestBase;
import com.oponeo.ordersystem.integration.support.CustomerControllerTestSupport;
import com.oponeo.ordersystem.util.DtoFixtures;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class CustomerControllerIT extends RestAssuredIntegrationTestBase implements CustomerControllerTestSupport {

    @Test
    void shouldRegisterAndGetCustomer() {
        // given
        CustomerRequestDto customerRequest = DtoFixtures.someCustomerRequest();

        // when
        CustomerResponseDto createdCustomer = registerCustomer(customerRequest);
        CustomerResponseDto fetchedCustomer = getCustomerById(createdCustomer.id());

        // then
        assertThat(createdCustomer.id()).isNotNull();
        assertThat(createdCustomer.name()).isEqualTo(customerRequest.name());
        assertThat(fetchedCustomer.email()).isEqualTo(customerRequest.email());
        assertThat(fetchedCustomer.name()).isEqualTo(customerRequest.name());
    }

    @Test
    void shouldReturn400WhenEmailIsInvalid() {
        // given
        CustomerRequestDto invalidCustomer = CustomerRequestDto.builder()
                .email("invalid-email")
                .name("John Doe")
                .phone("+48123456789")
                .addressLine("Baker Street 221b")
                .city("London")
                .postalCode("00-000")
                .country("England")
                .build();

        // when & then
        given()
                .spec(requestSpecification())
                .body(invalidCustomer)
                .when()
                .post(CustomerController.API_CUSTOMER)
                .then()
                .statusCode(BAD_REQUEST.value());
    }

    @Test
    void shouldReturn404WhenCustomerNotFound() {
        // given
        long nonExistingCustomerId = 99L;

        // when & then
        given()
                .spec(requestSpecification())
                .when()
                .get(CustomerController.API_CUSTOMER + "/" + nonExistingCustomerId)
                .then()
                .statusCode(NOT_FOUND.value());
    }

}
