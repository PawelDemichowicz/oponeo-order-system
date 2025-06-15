package com.oponeo.ordersystem.database.repository;

import com.oponeo.ordersystem.database.repository.jpa.OrderItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderItemRepository {

    private final OrderItemJpaRepository orderItemJpaRepository;

    public void deleteAll() {
        orderItemJpaRepository.deleteAll();
    }
}
