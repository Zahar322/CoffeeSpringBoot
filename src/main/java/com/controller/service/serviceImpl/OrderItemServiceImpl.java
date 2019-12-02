package com.controller.service.serviceImpl;

import com.controller.entity.OrderItem;
import com.controller.repo.OrderItemRepository;
import com.controller.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository itemRepository;

    @Override
    public OrderItem save(OrderItem orderItem) {
        return itemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> findAllByOrderId(int orderId) {
        return itemRepository.findAllByOrderId(orderId);
    }

    @Override
    public void saveAll(List<Integer> amounts, List<Integer> coffeeIds, int orderId) {
        for(int i=0;i<amounts.size();i++){
            OrderItem item=new OrderItem();
            item.setAmount(amounts.get(i));
            item.setCoffeeId(coffeeIds.get(i));
            item.setOrderId(orderId);
            itemRepository.save(item);
        }
    }
}
