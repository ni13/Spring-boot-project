package com.app.myntra.Controller;

import com.app.myntra.Entity.Cart;
import com.app.myntra.Entity.User;
import com.app.myntra.Entity.Product;
import com.app.myntra.Repository.CartRepository;
import com.app.myntra.Repository.UserRepository;
import com.app.myntra.Repository.ProductRepository;
import com.app.myntra.model.AddToCartRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    // ✅ Get ALL cart items (for admin/debug use)
    @GetMapping
    public List<Cart> getAllCartItems() {
        return cartRepository.findAll();
    }

    // ✅ Get cart items for a specific user
    @GetMapping(params = "userId")
    public List<Cart> getCartByUserId(@RequestParam Long userId) {
        return cartRepository.findByUserId(userId);
    }

    // ✅ Get single cart item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartItemById(@PathVariable Long id) {
        return cartRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Add item to cart using userId + productId
    @PostMapping
    public ResponseEntity<Cart> addCartItem(@RequestBody AddToCartRequest request) {
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(request.getQuantity());

        return ResponseEntity.ok(cartRepository.save(cart));
    }

    // ✅ Update existing cart item
    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCartItem(@PathVariable Long id, @RequestBody Cart cartDetails) {
        return cartRepository.findById(id).map(cart -> {
            cart.setQuantity(cartDetails.getQuantity());
            cart.setPrice(cartDetails.getPrice());
            cart.setProduct(cartDetails.getProduct());
            cart.setUser(cartDetails.getUser());
            return ResponseEntity.ok(cartRepository.save(cart));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete cart item
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCartItem(@PathVariable Long id) {
        return cartRepository.findById(id).map(cart -> {
            cartRepository.delete(cart);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
