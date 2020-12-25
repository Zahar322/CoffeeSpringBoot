package com.controller.service.serviceImpl;

import com.controller.entity.Coffee;
import com.controller.repo.CoffeeRepository;
import com.controller.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CoffeeServiceImpl implements CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    private List<Coffee> coffeeList;

    private Map<Integer,Coffee> coffeePrices;

    @Override
    public List<Coffee> findAll() {
        return coffeeRepository.findAll();
    }

    @Override
    public Coffee findById(int id) {
        return coffeeRepository.getOne(id);
    }

    @Override
    public Coffee save(Coffee coffee) {
        return coffeeRepository.save(coffee);
    }

    @Override
    public int getTotalPrice(List<Integer> coffeeIds, List<Integer> counts) {
        List<Integer> prices = coffeeRepository.findAllById(coffeeIds).stream()
                                                                      .map(Coffee::getPrice)
                                                                      .collect(Collectors.toList());
        int total = 0;
        for (int i = 0; i < prices.size(); i++) {
            total = total + prices.get(i) * counts.get(i);
        }
        return total;
    }
}
