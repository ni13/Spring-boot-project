package com.app.myntra.Service;
import com.app.myntra.Entity.Product;
import com.app.myntra.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Productseervice {
    @Autowired
private ProductRepository productRepo;

public Product createProduct(Product product) {
    return productRepo.save(product);
}

public Product getProduct(Long id) {
    return productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
}

public List<Product> getAllProducts() {
    return productRepo.findAll();
}

public Product updateProduct(Long id, Product updatedProduct) {
    Product existing = getProduct(id);
    existing.setName(updatedProduct.getName());
    existing.setBrand(updatedProduct.getBrand());
    existing.setPrice(updatedProduct.getPrice());
    existing.setCategory(updatedProduct.getCategory());
    return productRepo.save(existing);
}

public void deleteProduct(Long id) {
    productRepo.deleteById(id);
}

}
