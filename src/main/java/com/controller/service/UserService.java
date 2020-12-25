package com.controller.service;

import com.controller.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByUsername(String username);

    User findByUsernameAndPassword(String username,String password);

    User save(User user);

    void deleteById(int id);

    User update(User user);

    User save(User user, MultipartFile file, String description) throws IOException;
}
