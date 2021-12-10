package com.cg.shopping.cartservice.controller;

import com.cg.shopping.cartservice.entity.Cart;
import com.cg.shopping.cartservice.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/createCart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cart> addCart(@RequestBody Cart cart) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartService.addCart(cart));
    }


    @GetMapping(value = "/allCart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cart>> getAllCart() {
        return ResponseEntity.ok(cartService.getAllCart());
    }


    @GetMapping(value = "/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cart> getById(@RequestParam (value = "cartId") int cartId ) {
        return ResponseEntity.ok(cartService.getCartById(cartId));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cart> update(@RequestBody Cart cart) {
        return ResponseEntity.ok(cartService.updateCart(cart));
    }
}
