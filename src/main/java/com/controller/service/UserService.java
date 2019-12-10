package com.controller.service;

import com.controller.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByUsername(String username);

    User findByUsernameAndPassword(String username,String password);

    User save(User user);
}
