package com.app.myntra.Controller;

import com.app.myntra.Entity.Order;
import com.app.myntra.Repository.OrderRepository;
import com.app.myntra.Service.Orderservice;
import com.app.myntra.model.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Orderservice orderService;

    // ✅ Create order using userId from OrderRequest
 @PostMapping
public ResponseEntity<Map<String, Object>> placeOrder(@RequestBody OrderRequest request) {
    Order order = orderService.placeOrder(request.getUserId());

    Map<String, Object> response = new HashMap<>();
    response.put("message", "Order placed successfully");
    response.put("orderId", order.getId());

    return ResponseEntity.ok(response); // ✅ custom JSON response
}

    // ✅ Read all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // ✅ Read one order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✏️ Update order
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        return orderRepository.findById(id).map(order -> {
            order.setStatus(orderDetails.getStatus());
            order.setTotalAmount(orderDetails.getTotalAmount());
            order.setUser(orderDetails.getUser());
            orderRepository.save(order);
            return ResponseEntity.ok(order);
        }).orElse(ResponseEntity.notFound().build());
    }

    // ❌ Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        return orderRepository.findById(id).map(order -> {
            orderRepository.delete(order);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
