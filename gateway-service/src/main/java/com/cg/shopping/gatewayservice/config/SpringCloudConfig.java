package com.cg.shopping.gatewayservice.config;

import com.cg.shopping.gatewayservice.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {

    //@Autowired
    //private JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/auth/**")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .route("product-service", r -> r.path("/api/products/**")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://product-service"))
                .route("profile-service", r -> r.path("/api/profile/**")
                        .uri("lb://profile-service"))
                .route("cart-service", r -> r.path("/cart/**")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://cart-service"))
                .route("order-service", r -> r.path("/api/orders/**")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://order-service"))
                .route("wallet-service", r -> r.path("/api/wallet/**")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://wallet-service"))
                .build();
    }
}
