package com.cg.shopping.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "CART-SERVICE", url = "http://localhost:8080/api/cart", configuration = MyRestTemplateConfig.class)
public interface CartServiceClient {
    @GetMapping("")
    Cart getCart(@RequestHeader("Authorization") String auth);
}
