package com.order.mgmt.service.service;

import com.order.mgmt.service.dto.CreateOrderRequest;
import com.order.mgmt.service.entities.Order;
import com.order.mgmt.service.entities.OrderStatus;

import java.util.List;

public interface OrderService {

    Order createOrder(CreateOrderRequest request);

    Order getOrderById(String orderId);

    Order updateOrderStatus(String orderId, OrderStatus status);

    List<Order> getAllOrders();
}
