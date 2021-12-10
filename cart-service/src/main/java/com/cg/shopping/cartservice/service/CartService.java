package com.cg.shopping.cartservice.service;

import com.cg.shopping.cartservice.dao.CartRepository;
import com.cg.shopping.cartservice.entity.Cart;
import com.cg.shopping.cartservice.entity.Items;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;

    public Cart getCartById(int cartId) {
        return cartRepository.findByCartId(cartId).orElse(Cart.builder().build());
    }

    public Cart updateCart(Cart cart) {
        Optional<Cart> byCartId = cartRepository.findByCartId(cart.getCartId());
        if (byCartId.isPresent()) {
            cart.setId(byCartId.get().getId());
            cart.setTotalPrice(cartTotal(cart));
            return cartRepository.save(cart);
        }

        return Cart.builder().build();
    }

    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }

    public double cartTotal(Cart cart) {
        return cart.getItems().stream().mapToDouble(Items::getPrice).sum();
    }

    public Cart addCart(Cart cart) {
        cart.setCartId(getNextId());
        cart.setTotalPrice(cartTotal(cart));
        return  cartRepository.save(cart);
    }
    
    @Synchronized
    public int getNextId() {
        Cart cart = cartRepository.findTopByOrderByCartIdDesc();
        int id = (cart != null) ? cart.getCartId() : 0;
        return ++id;
    }

}
