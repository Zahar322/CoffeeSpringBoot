package com.controller.repo;

import com.controller.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findAllByCoffeeId(int coffeeId);

    List<Comment> findAllByUserId(int userId);

    List<Comment> findAllByCoffeeIdOrderByIdDesc(int coffeeId);

    Comment save(Comment comment);




}
