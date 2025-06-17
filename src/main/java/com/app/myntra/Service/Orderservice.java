package com.app.myntra.Service;
import com.app.myntra.Entity.Order;
import com.app.myntra.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Orderservice {
  @Autowired
private OrderRepository orderRepo;

public Order createOrder(Order order) {
    return orderRepo.save(order);
}

public Order getOrder(Long id) {
    return orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
}

public List<Order> getAllOrders() {
    return orderRepo.findAll();
}

public Order updateOrder(Long id, Order updatedOrder) {
    Order existing = getOrder(id);
    existing.setStatus(updatedOrder.getStatus());
    existing.setOrderDate(updatedOrder.getOrderDate());
    existing.setTotalAmount(updatedOrder.getTotalAmount());
    return orderRepo.save(existing);
}

public void deleteOrder(Long id) {
    orderRepo.deleteById(id);
}
  
}
