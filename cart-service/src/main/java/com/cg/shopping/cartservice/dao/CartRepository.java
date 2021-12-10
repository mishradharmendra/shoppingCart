package com.cg.shopping.cartservice.dao;

import com.cg.shopping.cartservice.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

    Optional<Cart> findByCartId(int cartId);
    Cart findTopByOrderByCartIdDesc();

}
