package com.controller.service;

import com.controller.entity.Coffee;

import java.util.List;
import java.util.Map;

public interface CoffeeService {

    List<Coffee> findAll();

    List<Coffee> getCoffeeList();

    Map<Integer, Coffee> getCoffeePrices();

    Coffee findById(int id);
}
