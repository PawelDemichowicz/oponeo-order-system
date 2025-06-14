package com.oponeo.ordersystem.business.service.impl;

import com.oponeo.ordersystem.business.domain.Product;
import com.oponeo.ordersystem.business.service.ProductService;
import com.oponeo.ordersystem.database.repository.ProductRepository;
import com.oponeo.ordersystem.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Could not find product by id: [%s]".formatted(productId)));
    }
}