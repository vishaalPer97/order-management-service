package com.order.mgmt.service;

import com.order.mgmt.service.dto.CreateOrderRequest;
import com.order.mgmt.service.entities.Order;
import com.order.mgmt.service.entities.OrderStatus;
import com.order.mgmt.service.exceptions.InvalidStatusUpdateException;
import com.order.mgmt.service.exceptions.OrderNotFoundException;
import com.order.mgmt.service.repository.InMemoryOrderRepository;
import com.order.mgmt.service.repository.OrderRepository;
import com.order.mgmt.service.service.OrderService;
import com.order.mgmt.service.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        OrderRepository repository = new InMemoryOrderRepository();
        orderService = new OrderServiceImpl(repository);
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerName("Vishal");
        request.setAmount(new BigDecimal("1000.0"));

        Order order = orderService.createOrder(request);

        assertNotNull(order);
        assertNotNull(order.getOrderId());
        assertEquals("Vishal", order.getCustomerName());
        assertEquals(0, new BigDecimal("1000.0").compareTo(order.getAmount()));
        assertEquals(OrderStatus.NEW, order.getStatus());
    }

    @Test
    void shouldReturnOrderById() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerName("Test");
        request.setAmount(new BigDecimal("200.0"));

        Order createdOrder = orderService.createOrder(request);

        Order fetchedOrder = orderService.getOrderById(createdOrder.getOrderId());

        assertEquals(createdOrder.getOrderId(), fetchedOrder.getOrderId());
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {
        assertThrows(OrderNotFoundException.class,() -> orderService.getOrderById("invalid-id"));
    }

    @Test
    void shouldUpdateStatusFromNewToProcessing() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerName("User");
        request.setAmount(new BigDecimal("300.0"));

        Order order = orderService.createOrder(request);

        Order updated = orderService.updateOrderStatus(
                order.getOrderId(),
                OrderStatus.PROCESSING
        );

        assertEquals(OrderStatus.PROCESSING, updated.getStatus());
    }

    @Test
    void shouldUpdateStatusFromProcessingToCompleted() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerName("User");
        request.setAmount(new BigDecimal("300.0"));

        Order order = orderService.createOrder(request);

        orderService.updateOrderStatus(order.getOrderId(), OrderStatus.PROCESSING);

        Order updated = orderService.updateOrderStatus(
                order.getOrderId(),
                OrderStatus.COMPLETED
        );

        assertEquals(OrderStatus.COMPLETED, updated.getStatus());
    }

    @Test
    void shouldThrowExceptionForInvalidStatusTransition() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerName("User");
        request.setAmount(new BigDecimal("300.0"));

        Order order = orderService.createOrder(request);

        assertThrows(InvalidStatusUpdateException.class,
                () -> orderService.updateOrderStatus(
                        order.getOrderId(),
                        OrderStatus.COMPLETED));
    }

    @Test
    void shouldReturnAllOrders() {
        CreateOrderRequest request1 = new CreateOrderRequest();
        request1.setCustomerName("User1");
        request1.setAmount(new BigDecimal("100.0"));

        CreateOrderRequest request2 = new CreateOrderRequest();
        request2.setCustomerName("User2");
        request2.setAmount(new BigDecimal("200.0"));

        orderService.createOrder(request1);
        orderService.createOrder(request2);

        List<Order> orders = orderService.getAllOrders();

        assertEquals(2, orders.size());
    }
}

