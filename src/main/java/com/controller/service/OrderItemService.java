package com.controller.service;

import com.controller.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    OrderItem save(OrderItem orderItem);

    List<OrderItem> findAllByOrderId(int orderId);

    void saveAll(List<Integer> amounts,List<Integer> coffeeIds,int orderId);
}
