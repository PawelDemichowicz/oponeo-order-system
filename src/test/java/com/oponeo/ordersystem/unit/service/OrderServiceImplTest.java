package com.oponeo.ordersystem.unit.service;

import com.oponeo.ordersystem.business.domain.Customer;
import com.oponeo.ordersystem.business.domain.Order;
import com.oponeo.ordersystem.business.domain.OrderItem;
import com.oponeo.ordersystem.business.domain.Product;
import com.oponeo.ordersystem.business.service.CustomerService;
import com.oponeo.ordersystem.business.service.ProductService;
import com.oponeo.ordersystem.business.service.impl.OrderServiceImpl;
import com.oponeo.ordersystem.database.repository.OrderRepository;
import com.oponeo.ordersystem.exception.NotFoundException;
import com.oponeo.ordersystem.exception.ProcessingException;
import com.oponeo.ordersystem.util.DomainFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Customer customer;
    private Product product;
    private OrderItem orderItem;

    @BeforeEach
    void setup() {
        customer = DomainFixtures.someCustomer();
        product = DomainFixtures.someProduct();
        orderItem = DomainFixtures.someOrderItem();
    }

    @Test
    void shouldPlaceOrderSuccessfully() {
        // given
        Order order = Order.builder()
                .customer(customer)
                .orderItems(Set.of(orderItem))
                .build();

        when(customerService.getCustomerById(customer.getId())).thenReturn(customer);
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        // when
        Order result = orderService.placeOrder(order);

        // then
        assertNotNull(result);
        assertEquals(customer, result.getCustomer());
        assertEquals(1, result.getOrderItems().size());
        assertEquals(new BigDecimal("200.00"), result.getNetValue());
        assertEquals(new BigDecimal("246.00"), result.getGrossValue());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void shouldThrowWhenCustomerNotFound() {
        // given
        Long missingId = 99L;
        Order order = Order.builder()
                .customer(Customer.builder().id(missingId).build())
                .orderItems(Set.of(orderItem))
                .build();

        when(customerService.getCustomerById(missingId))
                .thenThrow(new NotFoundException("Could not find customer by id: [%s]".formatted(missingId)));

        // when & then
        Throwable exception = assertThrows(NotFoundException.class, () -> orderService.placeOrder(order));
        assertEquals("Could not find customer by id: [%s]".formatted(missingId), exception.getMessage());
    }


    @Test
    void shouldThrowWhenProductNotFound() {
        // given
        Order order = Order.builder()
                .customer(customer)
                .orderItems(Set.of(orderItem))
                .build();
        Long productId = order.getOrderItems().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Missing order item"))
                .getProduct()
                .getId();

        when(customerService.getCustomerById(customer.getId())).thenReturn(customer);
        when(productService.getProductById(productId)).thenThrow(
                new NotFoundException("Could not find product by id: [%s]".formatted(productId)));

        // when & then
        Throwable exception = assertThrows(NotFoundException.class,
                () -> orderService.placeOrder(order));
        assertEquals("Could not find product by id: [%s]".formatted(productId),
                exception.getMessage());
    }

    @Test
    void shouldThrowWhenDuplicateProductsInOrder() {
        // given
        OrderItem duplicatedItem = OrderItem.builder().product(product).quantity(1).build();
        Order order = Order.builder()
                .customer(customer)
                .orderItems(Set.of(orderItem, duplicatedItem))
                .build();
        Long productId = order.getOrderItems().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Missing order item"))
                .getProduct()
                .getId();

        when(customerService.getCustomerById(customer.getId())).thenReturn(customer);
        when(productService.getProductById(productId)).thenReturn(product);

        // when & then
        Throwable exception = assertThrows(ProcessingException.class,
                () -> orderService.placeOrder(order));
        assertEquals("Order contains duplicate product id: [%s] ".formatted(productId),
                exception.getMessage());
    }

    @Test
    void shouldReturnOrderByOrderNumber() {
        // given
        Order expectedOrder = Order.builder().orderNumber("ORD-123").build();
        when(orderRepository.findByOrderNumber("ORD-123")).thenReturn(Optional.of(expectedOrder));

        // when
        Order result = orderService.getOrderByOrderNumber("ORD-123");

        // then
        assertEquals("ORD-123", result.getOrderNumber());
    }

    @Test
    void shouldThrowWhenOrderNumberNotFound() {
        // given
        String missingOrderNumber = "ORD-404";
        when(orderRepository.findByOrderNumber(missingOrderNumber)).thenReturn(Optional.empty());

        // when & then
        Throwable exception = assertThrows(NotFoundException.class,
                () -> orderService.getOrderByOrderNumber(missingOrderNumber));
        assertEquals("Could not find order by number: [%s]".formatted(missingOrderNumber),
                exception.getMessage());
    }
}
