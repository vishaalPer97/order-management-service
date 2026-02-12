package com.order.mgmt.service.repository;

import com.order.mgmt.service.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order saveOrder(Order order);

    Optional<Order> findOrderById(String orderId);

    List<Order> findAllOrders();
}
