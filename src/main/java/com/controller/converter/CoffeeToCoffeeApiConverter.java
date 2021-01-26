package com.controller.converter;

import api.Coffee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CoffeeToCoffeeApiConverter {

    public List<Coffee> convert(List<com.controller.entity.Coffee> coffees) {
        List<Coffee> coffeesApi = new ArrayList<>();
        coffees.forEach(coffee -> coffeesApi.add(new Coffee(coffee.getId(), coffee.getTitle(), coffee.getPrice(), coffee.getTitleRu(), coffee.getAbout())));
        return coffeesApi;
    }
}
