package com.oponeo.ordersystem.business.service;

import com.oponeo.ordersystem.business.domain.Order;

public interface OrderService {

    Order placeOrder(Order order);

    Order getOrderByOrderNumber(String orderNumber);
}