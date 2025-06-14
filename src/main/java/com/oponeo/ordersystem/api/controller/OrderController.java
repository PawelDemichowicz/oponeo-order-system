package com.oponeo.ordersystem.api.controller;

import com.oponeo.ordersystem.api.dto.request.OrderRequestDto;
import com.oponeo.ordersystem.api.dto.response.OrderResponseDto;
import com.oponeo.ordersystem.api.dto.mapper.OrderDtoMapper;
import com.oponeo.ordersystem.business.domain.Order;
import com.oponeo.ordersystem.business.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(OrderController.API_ORDER)
@RequiredArgsConstructor
public class OrderController {

    public static final String API_ORDER = "/orders";
    public static final String API_ORDER_NUMBER = "/{orderNumber}";

    private final OrderService orderService;
    private final OrderDtoMapper orderDtoMapper;

    @PostMapping
    public ResponseEntity<OrderResponseDto> placeOrder(
            @Valid @RequestBody OrderRequestDto orderRequestDto
    ) {
        Order savedOrder = orderService.placeOrder(orderDtoMapper.mapFromRequestDto(orderRequestDto));
        OrderResponseDto orderResponseDto = orderDtoMapper.mapToResponseDto(savedOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDto);
    }

    @GetMapping(API_ORDER_NUMBER)
    public OrderResponseDto getOrderByNumber(@PathVariable String orderNumber) {
        return orderDtoMapper.mapToResponseDto(orderService.getOrderByOrderNumber(orderNumber));
    }
}
