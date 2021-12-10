package com.cg.shopping.productservice.controller;

import com.cg.shopping.productservice.entity.Product;
import com.cg.shopping.productservice.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/createProduct", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createProduct(@RequestHeader(name = "X-User") String userId,
                                              @RequestBody Product product) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, String>> typeReference = new TypeReference<>(){};
        HashMap<String, String> map = mapper.readValue(userId, typeReference);
        Object roles = map.get("Roles");

        if( roles.equals("ROLES_ADMIN")) {
            log.info("Received Request from user " + userId);
            productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value = "/allProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllProduct(@RequestHeader(name = "X-User") String userId) {
        log.info("Received Request from user " + userId);
        return ResponseEntity.ok(productService.getAllProducts());
    }


    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Product>> getAllProductById(@RequestParam (value = "productId") int productId ) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping(value = "/productType/{productType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllProductByType(@RequestParam (value = "productType") String productType ) {
        return ResponseEntity.ok(productService.getProductsByProductType(productType));
    }

    @GetMapping(value = "/productName/{productName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Product>> getAllProductByName(@RequestParam (value = "productName") String productName ) {
        return ResponseEntity.ok(productService.getProductByName(productName));
    }

    @GetMapping(value = "/category/{productCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllProductByCategory(@RequestParam (value = "productCategory") String productCategory ) {
        return ResponseEntity.ok(productService.getProductsByCategory(productCategory));
    }

    @PutMapping(value="/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@RequestParam(value = "productId") int productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
