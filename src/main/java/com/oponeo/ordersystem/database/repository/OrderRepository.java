package com.oponeo.ordersystem.database.repository;

import com.oponeo.ordersystem.business.domain.Order;
import com.oponeo.ordersystem.database.entity.OrderEntity;
import com.oponeo.ordersystem.database.mapper.OrderEntityMapper;
import com.oponeo.ordersystem.database.mapper.OrderItemEntityMapper;
import com.oponeo.ordersystem.database.repository.jpa.OrderItemJpaRepository;
import com.oponeo.ordersystem.database.repository.jpa.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderItemJpaRepository orderItemJpaRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final OrderItemEntityMapper orderItemEntityMapper;

    public Optional<Order> findById(Long orderId) {
        return orderJpaRepository.findById(orderId)
                .map(orderEntityMapper::mapFromEntity);
    }

    public Optional<Order> findByOrderNumber(String orderNumber) {
        return orderJpaRepository.findByOrderNumber(orderNumber)
                .map(orderEntityMapper::mapFromEntity);
    }

    public Order save(Order order) {
        OrderEntity orderToSave = orderEntityMapper.mapToEntity(order);
        OrderEntity orderSaved = orderJpaRepository.saveAndFlush(orderToSave);

        order.getOrderItems().stream()
                .filter(orderItem -> Objects.isNull(orderItem.getId()))
                .map(orderItemEntityMapper::mapToEntity)
                .forEach(orderItemEntity -> {
                    orderItemEntity.setOrder(orderSaved);
                    orderItemJpaRepository.saveAndFlush(orderItemEntity);
                });
        return orderEntityMapper.mapFromEntity(orderSaved);
    }
}
