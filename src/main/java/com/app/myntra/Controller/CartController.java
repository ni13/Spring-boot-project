package com.app.myntra.Controller;

import com.app.myntra.Entity.Cart;
import com.app.myntra.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*") // optional: if calling from frontend like Postman or React
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @GetMapping
    public List<Cart> getAllCartItems() {
        return cartRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartItemById(@PathVariable Long id) {
        return cartRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

 @PostMapping
public ResponseEntity<Cart> addCartItem(@RequestBody Cart cart) {
    Cart savedCart = cartRepository.save(cart);
    return ResponseEntity.ok(savedCart);
}


    

    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCartItem(@PathVariable Long id, @RequestBody Cart cartDetails) {
        return cartRepository.findById(id).map(cart -> {
            cart.setQuantity(cartDetails.getQuantity());
            cart.setPrice(cartDetails.getPrice());
            cart.setProduct(cartDetails.getProduct());
            cart.setUser(cartDetails.getUser());
            cartRepository.save(cart);
            return ResponseEntity.ok(cart);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCartItem(@PathVariable Long id) {
        return cartRepository.findById(id).map(cart -> {
            cartRepository.delete(cart);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
