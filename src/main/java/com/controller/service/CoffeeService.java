package com.controller.service;

import com.controller.entity.Coffee;

import java.util.List;
import java.util.Map;

public interface CoffeeService {

    List<Coffee> findAll();

    Coffee findById(int id);

    Coffee save(Coffee coffee);

    int getTotalPrice(List<Integer> coffeeIds, List<Integer> counts);

}
