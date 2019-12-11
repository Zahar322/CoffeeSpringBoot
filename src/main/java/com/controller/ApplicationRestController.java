package com.controller;

import com.controller.entity.Comment;
import com.controller.entity.User;
import com.controller.service.CommentService;
import com.controller.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/rest",produces = "application/json")
public class ApplicationRestController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping("/comment/{coffeeId}")
    public List<Comment> commentList(@PathVariable int coffeeId){
        return commentService.findAllByCoffeeId(coffeeId);
    }

    @PostMapping("/saveComment")
    public Comment save(@RequestBody Comment comment){
        comment.setCreationDate(new Timestamp(new Date().getTime()));
        return commentService.save(comment);
    }

    @GetMapping("/users")
    public List<User> list(){
        return userService.findAll();
    }

}
