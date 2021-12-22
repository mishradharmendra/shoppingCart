package com.cg.shopping.orderservice.dao;

import com.cg.shopping.orderservice.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByCustomerId(String customerId);

    Optional<Order> findByOrderId(int orderId);

    Order findTopByOrderByOrderDateDesc();

    Order findTopByOrderByOrderIdDesc();

}
