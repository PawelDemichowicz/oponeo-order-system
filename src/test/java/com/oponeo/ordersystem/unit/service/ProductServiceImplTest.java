package com.oponeo.ordersystem.unit.service;

import com.oponeo.ordersystem.business.domain.Product;
import com.oponeo.ordersystem.business.service.impl.ProductServiceImpl;
import com.oponeo.ordersystem.database.repository.ProductRepository;
import com.oponeo.ordersystem.exception.NotFoundException;
import com.oponeo.ordersystem.util.DomainFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setup() {
        product = DomainFixtures.someProduct();
    }

    @Test
    void shouldAddProductSuccessfully() {
        // given
        when(productRepository.save(product)).thenReturn(product);

        // when
        Product result = productService.addProduct(product);

        // then
        assertNotNull(result);
        assertEquals(product, result);
    }

    @Test
    void shouldReturnProductById() {
        // given
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        // when
        Product result = productService.getProductById(product.getId());

        // then
        assertNotNull(result);
        assertEquals(product, result);
    }

    @Test
    void shouldThrowWhenProductNotFound() {
        // given
        Long missingId = 99L;
        when(productRepository.findById(missingId)).thenReturn(Optional.empty());

        // when & then
        Throwable exception = assertThrows(NotFoundException.class, () -> productService.getProductById(missingId));
        assertEquals("Could not find product by id: [%s]".formatted(missingId), exception.getMessage());
    }
}
