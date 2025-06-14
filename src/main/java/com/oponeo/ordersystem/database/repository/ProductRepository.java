package com.oponeo.ordersystem.database.repository;

import com.oponeo.ordersystem.business.domain.Product;
import com.oponeo.ordersystem.database.entity.ProductEntity;
import com.oponeo.ordersystem.database.repository.jpa.ProductJpaRepository;
import com.oponeo.ordersystem.database.mapper.ProductEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductEntityMapper productEntityMapper;

    public Product save(Product product) {
        ProductEntity productToSave = productEntityMapper.mapToEntity(product);
        return productEntityMapper.mapFromEntity(productJpaRepository.save(productToSave));
    }

    public Optional<Product> findById(Long productId) {
        return productJpaRepository.findById(productId)
                .map(productEntityMapper::mapFromEntity);
    }

    public List<Product> findAllById(List<Long> productIds) {
        return productJpaRepository.findAllById(productIds).stream()
                .map(productEntityMapper::mapFromEntity)
                .toList();
    }
}
