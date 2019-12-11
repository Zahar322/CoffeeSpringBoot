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

@Service
public class CoffeeSerivceImpl implements CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    private List<Coffee> coffeeList;

    private Map<Integer,Coffee> coffeePrices;

    @PostConstruct
    private void init(){
       coffeeList=findAll();
       coffeePrices=new HashMap<>();
       for(Coffee coffee:coffeeList){
           coffeePrices.put(coffee.getId(),coffee);
       }
    }

    @Override
    public List<Coffee> findAll() {
        return coffeeRepository.findAll();
    }

    public List<Coffee> getCoffeeList() {
        return coffeeList;
    }

    public Map<Integer, Coffee> getCoffeePrices() {
        return coffeePrices;
    }

    @Override
    public Coffee findById(int id) {
        return coffeeRepository.findById(id);
    }
}
