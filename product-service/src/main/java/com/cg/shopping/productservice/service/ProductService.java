package com.cg.shopping.productservice.service;

import com.cg.shopping.productservice.dao.ProductRepository;
import com.cg.shopping.productservice.entity.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(int productId) {
        Optional<Product> byProductId = productRepository.findByProductId(productId);
        if (byProductId.isPresent())
            productRepository.deleteById(byProductId.get().getId());
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
    public List<Product> getProductsByProductType(String productType) {
        return productRepository.findByProductType(productType);
    }


    public Optional<Product> getProductById(int productId) {
        return productRepository.findByProductId(productId);
    }

    public Optional<Product> getProductByName(String productName) {
        return productRepository.findByProductName(productName);
    }
}
