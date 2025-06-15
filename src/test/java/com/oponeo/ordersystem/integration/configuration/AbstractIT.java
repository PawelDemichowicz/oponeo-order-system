package com.oponeo.ordersystem.integration.configuration;

import com.oponeo.ordersystem.OrderSystemApplication;
import com.oponeo.ordersystem.database.repository.CustomerRepository;
import com.oponeo.ordersystem.database.repository.OrderItemRepository;
import com.oponeo.ordersystem.database.repository.OrderRepository;
import com.oponeo.ordersystem.database.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@Import(PersistenceContainerTestConfiguration.class)
@SpringBootTest(
        classes = OrderSystemApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class AbstractIT {

    @Autowired
    protected CustomerRepository customerRepository;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    protected OrderItemRepository orderItemRepository;

    @LocalServerPort
    protected int port;

    @Value("${server.servlet.context-path}")
    protected String basePath;

    @AfterEach
    public void cleanUp() {
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        productRepository.deleteAll();
        customerRepository.deleteAll();
    }
}
