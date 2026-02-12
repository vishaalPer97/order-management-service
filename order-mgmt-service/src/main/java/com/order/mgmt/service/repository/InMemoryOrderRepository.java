package com.order.mgmt.service.repository;

import com.order.mgmt.service.entities.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryOrderRepository implements OrderRepository{

    private final Map<String, Order> inMemoryStorage = new ConcurrentHashMap<>();

    @Override
    public Order saveOrder(Order order) {
        inMemoryStorage.put(order.getOrderId(), order);
        return order;
    }

    @Override
    public Optional<Order> findOrderById(String orderId) {
        return Optional.ofNullable(inMemoryStorage.get(orderId));
    }

    @Override
    public List<Order> findAllOrders() {
        return new ArrayList<>(inMemoryStorage.values());
    }
}
