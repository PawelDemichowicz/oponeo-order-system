package com.oponeo.ordersystem.integration.support;

import com.oponeo.ordersystem.api.controller.ProductController;
import com.oponeo.ordersystem.api.dto.request.ProductRequestDto;
import com.oponeo.ordersystem.api.dto.response.ProductResponseDto;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface ProductControllerTestSupport {

    RequestSpecification requestSpecification();

    default ProductResponseDto addProduct(ProductRequestDto productRequestDto) {
        return requestSpecification()
                .body(productRequestDto)
                .post(ProductController.API_PRODUCT)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ProductResponseDto.class);
    }

    default ProductResponseDto getProductById(Long productId) {
        return requestSpecification()
                .get(ProductController.API_PRODUCT + "/" + productId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ProductResponseDto.class);
    }
}
