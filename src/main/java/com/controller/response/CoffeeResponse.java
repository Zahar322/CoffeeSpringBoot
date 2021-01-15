package com.controller.response;

import com.controller.entity.Coffee;

import java.io.Serializable;
import java.util.List;

public class CoffeeResponse implements Serializable {

    private List<Coffee> coffeeList;

    public CoffeeResponse(List<Coffee> coffeeList) {
        this.coffeeList = coffeeList;
    }

    public CoffeeResponse() {
    }

    public List<Coffee> getCoffeeList() {
        return coffeeList;
    }

    public void setCoffeeList(List<Coffee> coffeeList) {
        this.coffeeList = coffeeList;
    }
}
