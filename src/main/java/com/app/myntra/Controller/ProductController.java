package com.app.myntra.Controller;

import com.app.myntra.Entity.Product;
import com.app.myntra.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Fetch all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Fetch product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Paginated and sorted products
    @GetMapping("/paged")
    public Page<Product> getPagedProducts(
        @PageableDefault(size = 5, sort = "price", direction = org.springframework.data.domain.Sort.Direction.ASC)
        Pageable pageable
    ) {
        return productRepository.findAll(pageable);
    }

    // ✅ FIXED: Add new product (works with application/json and application/json;charset=UTF-8)
@PostMapping
public Product createProduct(@RequestBody Product product) {
    System.out.println("✅ Received Product: " + product);
    return productRepository.save(product);
}



    // Update product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setBrand(productDetails.getBrand());
            product.setPrice(productDetails.getPrice());
            product.setCategory(productDetails.getCategory());
            productRepository.save(product);
            return ResponseEntity.ok(product);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
