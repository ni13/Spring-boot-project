package com.app.myntra.Service;

import com.app.myntra.Entity.Cart;
import com.app.myntra.Entity.Order;
import com.app.myntra.Entity.OrderItem;
import com.app.myntra.Entity.User;
import com.app.myntra.Repository.CartRepository;
import com.app.myntra.Repository.OrderItemRepository;
import com.app.myntra.Repository.OrderRepository;
import com.app.myntra.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Orderservice {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderItemRepository orderItemRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CartRepository cartRepo;

    public Order createOrder(Order order) {
        return orderRepo.save(order);
    }

    public Order getOrder(Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        Order existing = getOrder(id);
        existing.setStatus(updatedOrder.getStatus());
        existing.setTotalAmount(updatedOrder.getTotalAmount());
        return orderRepo.save(existing);
    }

    public void deleteOrder(Long id) {
        orderRepo.deleteById(id);
    }

    // ✅ PLACE ORDER METHOD (used by /orders endpoint)
    public Order placeOrder(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Cart> cartItems = cartRepo.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double totalAmount = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(totalAmount);
        order.setStatus("PLACED");

        Order savedOrder = orderRepo.save(order); // save order first

        // ✅ Create and save OrderItems
        for (Cart cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            orderItemRepo.save(orderItem);
        }

        // ✅ Clear the cart after order placed
        cartRepo.deleteAll(cartItems);

        return savedOrder;
    }
}
