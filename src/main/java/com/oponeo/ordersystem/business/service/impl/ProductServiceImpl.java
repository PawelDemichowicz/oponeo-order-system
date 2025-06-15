package com.oponeo.ordersystem.business.service.impl;

import com.oponeo.ordersystem.business.domain.Product;
import com.oponeo.ordersystem.business.service.ProductService;
import com.oponeo.ordersystem.database.repository.ProductRepository;
import com.oponeo.ordersystem.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product addProduct(Product product) {
        log.info("Adding new product: {}", product.getName());
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Could not find product by id: [%s]".formatted(productId)));
    }
}