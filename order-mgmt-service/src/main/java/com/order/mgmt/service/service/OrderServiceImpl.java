package com.order.mgmt.service.service;

import com.order.mgmt.service.dto.CreateOrderRequest;
import com.order.mgmt.service.entities.Order;
import com.order.mgmt.service.entities.OrderStatus;
import com.order.mgmt.service.exceptions.InvalidStatusUpdateException;
import com.order.mgmt.service.exceptions.OrderNotFoundException;
import com.order.mgmt.service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order createOrder(CreateOrderRequest request) {

        logger.info("Creating order for customer: {}", request.getCustomerName());

        String id = "ORD-" + UUID.randomUUID().toString().replace("-", "");

        Order order = Order.builder()
                .orderId(id)
                .customerName(request.getCustomerName())
                .amount(request.getAmount())
                .status(OrderStatus.NEW)
                .build();

        repository.saveOrder(order);

        logger.info("Order created successfully for customer: {} with id: {}", request.getCustomerName(), id);

        return order;
    }

    @Override
    public Order getOrderById(String orderId) {

        logger.debug("Fetching order with id: {}", orderId);

        return repository.findOrderById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id : " + orderId));
    }

    @Override
    public Order updateOrderStatus(String orderId, OrderStatus newStatus) {

        logger.info("Updating order {} to status {}", orderId, newStatus);

        Order order = getOrderById(orderId);

        validateTransition(order.getStatus(), newStatus);

        order.setStatus(newStatus);

        Order updatedOrder = repository.saveOrder(order);

        logger.info("Order {} updated successfully to {}", orderId, newStatus);

        return updatedOrder;
    }


    @Override
    public List<Order> getAllOrders() {

        logger.debug("Fetching all orders");

        List<Order> orders = repository.findAllOrders();

        logger.debug("Total orders fetched: {}", orders.size());

        return orders;
    }


    private void validateTransition(OrderStatus current, OrderStatus next) {

        logger.debug("Validating status transition from {} to {}", current, next);

        if (current == OrderStatus.NEW && next == OrderStatus.PROCESSING)
            return;

        if (current == OrderStatus.PROCESSING && next == OrderStatus.COMPLETED)
            return;

        logger.warn("Invalid order status transition from {} to {}", current, next);

        throw new InvalidStatusUpdateException("Invalid order status transition from " + current + " to " + next);
    }

}
