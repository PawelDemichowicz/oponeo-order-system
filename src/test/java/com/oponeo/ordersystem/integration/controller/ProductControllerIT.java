package com.oponeo.ordersystem.integration.controller;

import com.oponeo.ordersystem.api.controller.ProductController;
import com.oponeo.ordersystem.api.dto.request.ProductRequestDto;
import com.oponeo.ordersystem.api.dto.response.ProductResponseDto;
import com.oponeo.ordersystem.integration.configuration.RestAssuredIntegrationTestBase;
import com.oponeo.ordersystem.integration.support.ProductControllerTestSupport;
import com.oponeo.ordersystem.util.DtoFixtures;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class ProductControllerIT extends RestAssuredIntegrationTestBase implements ProductControllerTestSupport {

    @Test
    void shouldAddAndGetProduct() {
        // given
        ProductRequestDto productRequest = DtoFixtures.someProductRequest();

        // when
        ProductResponseDto createdProduct = addProduct(productRequest);
        ProductResponseDto fetchedProduct = getProductById(createdProduct.id());

        // then
        assertThat(createdProduct.id()).isNotNull();
        assertThat(createdProduct.name()).isEqualTo(productRequest.name());
        assertThat(fetchedProduct.id()).isEqualTo(createdProduct.id());
        assertThat(fetchedProduct.name()).isEqualTo(productRequest.name());
    }

    @Test
    void shouldReturn400WhenNetPriceIsNegative() {
        // given
        ProductRequestDto invalidProduct = ProductRequestDto.builder()
                .name("Invalid Product")
                .netPrice(new BigDecimal("-10.00"))
                .vatPercent(new BigDecimal("23.00"))
                .build();

        // when & then
        given()
                .spec(requestSpecification())
                .body(invalidProduct)
                .when()
                .post(ProductController.API_PRODUCT)
                .then()
                .statusCode(BAD_REQUEST.value());
    }

    @Test
    void shouldReturn404WhenProductNotFound() {
        // given
        long nonExistingProductId = 99L;

        // when & then
        given()
                .spec(requestSpecification())
                .when()
                .get(ProductController.API_PRODUCT + "/" + nonExistingProductId)
                .then()
                .statusCode(NOT_FOUND.value());
    }
}
