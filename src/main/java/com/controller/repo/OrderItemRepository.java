package com.controller.repo;

import com.controller.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {

    OrderItem save(OrderItem orderItem);

    List<OrderItem> findAllByOrderId(int orderId);

}
