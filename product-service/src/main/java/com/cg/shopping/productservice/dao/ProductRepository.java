package com.cg.shopping.productservice.dao;

import com.cg.shopping.productservice.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findByProductName(String productName);
    Optional<Product> findByProductId(int productId);

    List<Product> findByCategory(String category);

    List<Product> findByProductType(String productType);


}
