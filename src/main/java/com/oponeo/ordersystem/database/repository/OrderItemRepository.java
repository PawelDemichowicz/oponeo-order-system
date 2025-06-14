package com.oponeo.ordersystem.database.repository;

import com.oponeo.ordersystem.business.domain.OrderItem;
import com.oponeo.ordersystem.database.entity.OrderItemEntity;
import com.oponeo.ordersystem.database.repository.jpa.OrderItemJpaRepository;
import com.oponeo.ordersystem.database.mapper.OrderItemEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemRepository {

    private final OrderItemJpaRepository orderItemJpaRepository;
    private final OrderItemEntityMapper orderItemEntityMapper;

    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemJpaRepository.findByOrderId(orderId).stream()
                .map(orderItemEntityMapper::mapFromEntity)
                .toList();
    }

    public List<OrderItem> saveAll(List<OrderItem> orderItems) {
        List<OrderItemEntity> orderItemsToSave = orderItems.stream()
                .map(orderItemEntityMapper::mapToEntity)
                .toList();
        return orderItemJpaRepository.saveAll(orderItemsToSave).stream()
                .map(orderItemEntityMapper::mapFromEntity)
                .toList();
    }
}
