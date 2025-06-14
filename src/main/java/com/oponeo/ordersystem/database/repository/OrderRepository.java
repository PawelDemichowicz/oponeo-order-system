package com.oponeo.ordersystem.database.repository;

import com.oponeo.ordersystem.business.domain.Order;
import com.oponeo.ordersystem.database.entity.OrderEntity;
import com.oponeo.ordersystem.database.repository.jpa.OrderJpaRepository;
import com.oponeo.ordersystem.mapper.OrderEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderEntityMapper orderEntityMapper;

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
        return orderEntityMapper.mapFromEntity(orderJpaRepository.save(orderToSave));
    }
}
