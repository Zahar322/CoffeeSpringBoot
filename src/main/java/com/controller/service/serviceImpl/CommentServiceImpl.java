package com.controller.service.serviceImpl;

import com.controller.entity.Comment;
import com.controller.entity.User;
import com.controller.repo.CommentRepository;
import com.controller.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    @Transactional
    public void updateUserComments(User user) {
        for(Comment comment:commentRepository.findAllByUserId(user.getId())){
            comment.setUsername(user.getUsername());
            commentRepository.save(comment);
        }
    }

    @Override
    public List<Comment> findAllByCoffeeIdOrderByIdDesc(int coffeeId) {
        return commentRepository.findAllByCoffeeIdOrderByIdDesc(coffeeId);
    }
}
