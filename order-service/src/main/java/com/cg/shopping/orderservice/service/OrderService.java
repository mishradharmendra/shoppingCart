package com.cg.shopping.orderservice.service;

import com.cg.shopping.orderservice.dao.OrderRepository;
import com.cg.shopping.orderservice.entity.ShippingAddress;
import com.cg.shopping.orderservice.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order placeOrder(Order order) {
        order.setOrderDate(Instant.now());
        order.setOrderId(getNextId());
        order.setPaidAt(null);
        order.setIsPaid(false);
        return orderRepository.save(order);
    }

    public void changeOrderStatus(int orderId, String status) {
        Optional<Order> byOrderId = orderRepository.findByOrderId(orderId);

        if (byOrderId.isPresent()) {
            Order order = byOrderId.get();
            order.setOrderStatus(status);
            order.setIsPaid(true);
            order.setPaidAt(Instant.now());
            orderRepository.save(order);
        }

    }
    public void deleteOrder(int orderId) {
        Optional<Order> byOrderId = orderRepository.findByOrderId(orderId);

        byOrderId.ifPresent(orderRepository::delete);
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public Optional<Order> getOrderById(int orderId) {
        return orderRepository.findByOrderId(orderId);
    }
    public List<Order> getOrderByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Order getLatestOrder() {
        return orderRepository.findTopByOrderByOrderDateDesc();
    }

    @Synchronized
    public int getNextId() {
        Order order = orderRepository.findTopByOrderByOrderIdDesc();
        int id = (order != null) ? order.getOrderId() : 0;
        return ++id;
    }

    public Optional<Order> findById(String id) {
        return orderRepository.findById(id);
    }
}
