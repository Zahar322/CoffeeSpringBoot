package com.controller.service;

import com.controller.entity.Order;
import java.util.List;

public interface OrderService {

    Order save(Order order);

    List<Order> findAllByUserId(int userId);

    int findLastOrderId();
}
