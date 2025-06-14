package com.oponeo.ordersystem.business.service;

import com.oponeo.ordersystem.business.domain.Product;

public interface ProductService {

    Product addProduct(Product product);

    Product getProductById(Long productId);
}