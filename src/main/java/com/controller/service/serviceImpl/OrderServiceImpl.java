package com.controller.service.serviceImpl;

import com.controller.repo.OrderRepository;
import com.controller.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controller.entity.Order;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAllByUserId(int userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public int findLastOrderId() {
        return orderRepository.findTop1ByOrderByIdDesc().getId();
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        orderRepository.deleteById(id);
    }
}
