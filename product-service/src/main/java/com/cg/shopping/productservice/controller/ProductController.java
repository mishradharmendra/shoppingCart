package com.cg.shopping.productservice.controller;

import com.cg.shopping.productservice.entity.Product;
import com.cg.shopping.productservice.entity.TopProductRes;
import com.cg.shopping.productservice.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/products")
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

    @GetMapping("/top")
    public ResponseEntity<?> getTopProducts(){

        List<TopProductRes> products;
        try(Stream<TopProductRes> stream = productService.getTopProducts()) {
            products = stream.limit(3).collect(Collectors.toList());
        }
        System.out.println(products);
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getProducts(@RequestParam(value = "keyword") String keyword,
                                                           @RequestParam(value = "pageNumber") int pageNumber){
        String key = keyword.strip();
        Pageable pageable = PageRequest.of(pageNumber - 1, 8);
        if (key.length() == 0){
            Page<Product> products = productService.getAllProducts(pageable);
            List<Product> p = products.getContent();
            int page = products.getNumber() + 1;
            int pages = products.getTotalPages();
            Map<String, Object> result = Map.of("page", page, "pages", pages, "products", p);
            return ResponseEntity.ok(result);
        } else {
            Page<Product> products = productService.findAllByQ(key, pageable);
            List<Product> p = products.getContent();
            int page = products.getNumber() + 1;
            int pages = products.getTotalPages();
            Map<String, Object> result = Map.of("page", page, "pages", pages, "products", p);
            return ResponseEntity.ok(result);
        }

    }


    @GetMapping(value = "/allProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllProduct(@RequestHeader(name = "X-User") String userId) {
        log.info("Received Request from user " + userId);
        return ResponseEntity.ok(productService.getAllProducts());
    }


    @GetMapping(value = "/productId/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Product>> getAllProductById(@RequestParam (value = "productId") int productId ) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping(value = "/productName/{productName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Product>> getAllProductByName(@RequestParam (value = "productName") String productName ) {
        return ResponseEntity.ok(productService.getProductByName(productName));
    }

    @GetMapping(value = "/category/{productCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllProductByCategory(@RequestParam (value = "productCategory") String productCategory ) {
        return ResponseEntity.ok(productService.getProductsByCategory(productCategory));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@RequestParam(value = "productId") int productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Product p = productService.findById(id).get();
        return ResponseEntity.ok(p);
    }

    /*@PostMapping("/{id}/reviews")
    public ResponseEntity<?> addComment(Authentication auth, @PathVariable String id, @RequestBody CommentReq req){
        User user = userRepo.findByEmail(auth.getName()).get();
        Optional<Product> p = productRepo.findById(id);
        if (p.isPresent()){
            Product product = p.get();
            var alreadyReviewed = product.getReviews().stream().filter(x -> x.getUser().equals(user.get_id())).count();
            if (alreadyReviewed != 0){
                return ResponseEntity.status(400).body(new MessageRes("Product already reviewed"));
            }
            Product.Review r = new Product.Review();
            r.setRating(req.getRating());
            r.setComment(req.getComment());
            r.setName(user.getName());
            r.setUser(user.get_id());
            product.getReviews().add(r);
            int numReviews = product.getNumReviews() + 1;
            Double rating = (product.getRating() + req.getRating())/numReviews;
            product.setNumReviews(numReviews);
            DecimalFormat df = new DecimalFormat("#.0");
            product.setRating(Double.valueOf(df.format(rating)));
            productRepo.save(product);
            return ResponseEntity.status(201).body(new MessageRes("Review added"));

        }
        return ResponseEntity.ok(p);
    }*/
}
