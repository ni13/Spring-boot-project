package com.app.myntra.Service;
import com.app.myntra.Entity.Cart;
import com.app.myntra.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Cartservice {
 @Autowired
private CartRepository cartRepository;

public Cart createCart(Cart cart) {
    return cartRepository.save(cart);
}

public Cart getCart(Long id) {
    return cartRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
}

public List<Cart> getAllCarts() {
    return cartRepository.findAll();
}

public Cart updateCart(Long id, Cart updatedCart) {
    Cart existing = getCart(id);
    existing.setQuantity(updatedCart.getQuantity());
    existing.setPrice(updatedCart.getPrice());
    existing.setProduct(updatedCart.getProduct());
    existing.setUser(updatedCart.getUser());
    return cartRepository.save(existing);
}

public void deleteCart(Long id) {
    cartRepository.deleteById(id);
}
}