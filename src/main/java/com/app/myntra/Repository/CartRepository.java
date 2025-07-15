package com.app.myntra.Repository;

import com.app.myntra.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    // ✅ Get all cart items for a specific user
    List<Cart> findByUserId(Long userId);
}
