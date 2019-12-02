package com.controller.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.controller.entity.Order;
import java.util.List;

public interface OrderRepository  extends JpaRepository<Order,Integer> {

    Order save(Order order);

    List<Order> findAllByUserId(int userId);

    Order findTop1ByOrderByIdDesc();

}
