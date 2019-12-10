package com.controller.repo;

import com.controller.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    List<User> findAll();

    User findByUsername(String username);

    User findByUsernameAndPassword(String username,String password);

    User save(User user);


}
