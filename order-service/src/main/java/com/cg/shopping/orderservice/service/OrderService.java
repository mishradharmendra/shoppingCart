package com.cg.shopping.orderservice.service;

import com.cg.shopping.orderservice.dao.AddressRepository;
import com.cg.shopping.orderservice.dao.OrderRepository;
import com.cg.shopping.orderservice.entity.Address;
import com.cg.shopping.orderservice.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;

    public Order placeOrder(Order order) {
        order.setOrderId(getNextId()):
        return orderRepository.save(order);
    }

    public Address storeAddress(Address address) {
        return addressRepository.save(address);
    }
    public void changeOrderStatus(int orderId, String status) {
        Optional<Order> byOrderId = orderRepository.findByOrderId(orderId);

        if (byOrderId.isPresent()) {
            Order order = byOrderId.get();
            order.setOrderStatus(status);
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
    public List<Order> getOrderByCustomerId(int customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }

    public Order getLatestOrder() {
        return orderRepository.findTopByOrderByOrderDateDesc();
    }

    public List<Address> getAllAddressByCustomerId(int customerId) {
        return addressRepository.findByCustomerId(customerId);
    }
    
    @Synchronized
    public int getNextId() {
        Order order = orderRepository.findTopByOrderByOrderIdDesc();
        int id = (order != null) ? order.getOrderId() : 0;
        return ++id;
    }
}
