package com.controller.service;

import com.controller.entity.Comment;
import com.controller.entity.User;

import java.util.List;

public interface CommentService {

    List<Comment> findAllByCoffeeId(int coffeeId);

    List<Comment> findAllByUserId(int userId);

    Comment save(Comment comment);

    List<Comment> findAllByCoffeeIdOrderByIdDesc(int coffeeId);

    void updateUserComments(User user);
}
