package com.cg.shopping.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document(collection = "product")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Product {
    @Id
    private String id;

    private int productId;
    private String productType;
    private String productName;
    private String category;
    private Map<Integer, Double> rating;
    private Map<Integer, String> review;
    private List<String> imageList;
    private double price;
    private String description;
    private Map<String, String> specification;

}
