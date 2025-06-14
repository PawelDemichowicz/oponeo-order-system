package com.oponeo.ordersystem.business.service.impl;

import com.oponeo.ordersystem.business.domain.Customer;
import com.oponeo.ordersystem.business.domain.Order;
import com.oponeo.ordersystem.business.domain.OrderItem;
import com.oponeo.ordersystem.business.domain.Product;
import com.oponeo.ordersystem.business.service.CustomerService;
import com.oponeo.ordersystem.business.service.OrderService;
import com.oponeo.ordersystem.business.service.ProductService;
import com.oponeo.ordersystem.database.repository.OrderRepository;
import com.oponeo.ordersystem.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Override
    @Transactional
    public Order placeOrder(Order order) {
        Customer customer = customerService.getCustomerById(order.getCustomer().getId());

        Set<OrderItem> resolvedOrderItems = order.getOrderItems().stream()
                .map(orderItem -> {
                    Product product = productService.getProductById(orderItem.getProduct().getId());
                    return orderItem.withProduct(product);
                })
                .collect(Collectors.toSet());

        BigDecimal netTotal = resolvedOrderItems.stream()
                .map(orderItem ->
                        orderItem.getProduct().getNetPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal vatTotal = resolvedOrderItems.stream()
                .map(orderItem -> {
                    BigDecimal net = orderItem.getProduct().getNetPrice();
                    BigDecimal vatRate = orderItem.getProduct().getVatPercent()
                            .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
                    return net.multiply(vatRate).multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal grossTotal = netTotal.add(vatTotal);

        Order orderToSave = buildOrder(customer, resolvedOrderItems, netTotal, grossTotal);

        return orderRepository.save(orderToSave);
    }

    @Override
    public Order getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber).orElseThrow(
                () -> new NotFoundException("Could not find order by number: [%s]".formatted(orderNumber)));
    }

    private String generateOrderNumber() {
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String id = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return "ORD-" + now + "-" + id;
    }

    private Order buildOrder(
            Customer customer,
            Set<OrderItem> orderItems,
            BigDecimal netTotal,
            BigDecimal grossTotal
    ) {
        return Order.builder()
                .orderNumber(generateOrderNumber())
                .customer(customer)
                .orderItems(orderItems)
                .netValue(netTotal)
                .grossValue(grossTotal)
                .createdAt(LocalDateTime.now())
                .build();
    }
}