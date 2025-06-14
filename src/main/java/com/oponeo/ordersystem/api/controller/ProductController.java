package com.oponeo.ordersystem.api.controller;

import com.oponeo.ordersystem.api.dto.request.ProductRequestDto;
import com.oponeo.ordersystem.api.dto.response.ProductResponseDto;
import com.oponeo.ordersystem.api.dto.mapper.ProductDtoMapper;
import com.oponeo.ordersystem.business.domain.Product;
import com.oponeo.ordersystem.business.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ProductController.API_PRODUCT)
@RequiredArgsConstructor
public class ProductController {

    public static final String API_PRODUCT = "/products";
    public static final String API_PRODUCT_ID = "/{productId}";

    private final ProductService productService;
    private final ProductDtoMapper productDtoMapper;

    @PostMapping
    public ResponseEntity<ProductResponseDto> addProduct(
            @Valid @RequestBody ProductRequestDto productRequestDto
    ) {
        Product savedProduct = productService.addProduct(productDtoMapper.mapFromRequestDto(productRequestDto));
        ProductResponseDto productResponseDto = productDtoMapper.mapToResponseDto(savedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDto);
    }

    @GetMapping(value = API_PRODUCT_ID)
    public ProductResponseDto getProductById(@PathVariable Long productId) {
        return productDtoMapper.mapToResponseDto(productService.getProductById(productId));
    }
}
