package com.controller.service.serviceImpl;

import com.controller.entity.Comment;
import com.controller.repo.CommentRepository;
import com.controller.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findAllByCoffeeId(int coffeeId) {
        return commentRepository.findAllByCoffeeId(coffeeId);
    }

    @Override
    public List<Comment> findAllByUserId(int userId) {
        return commentRepository.findAllByUserId(userId);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> updateList(List<Comment> comments, String username) {
        for(int i=0;i<comments.size();i++){
            Comment comment=comments.get(i);
            comment.setUsername(username);
            commentRepository.save(comment);
        }
        return null;
    }
}
