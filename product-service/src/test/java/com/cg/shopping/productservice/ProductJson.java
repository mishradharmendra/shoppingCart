package com.cg.shopping.productservice;

import com.cg.shopping.productservice.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class ProductJson {

    @Test
    public void generateJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Product product = Product.builder()
                .productType("abc")
                .productName("lcu")
                .category("soap")
                .rating(Map.of(1, 5.0))
                .review(Map.of(1, "Bad"))
                .imageList(List.of("httt"))
                .price(122.5)
                .description("soao")
                .specification(Map.of("soap", "bath"))
                .build();

        System.out.println(mapper.writeValueAsString(product));
    }
}
