package com.controller.repo;

import com.controller.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CoffeeRepository extends JpaRepository<Coffee,Integer> {

    List<Coffee> findAll();
}
