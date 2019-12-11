package com.controller;

import com.controller.entity.User;
import com.controller.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/showCoffees")
    public String showCoffee(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("user",user);
        model.addAttribute("coffees",coffeeService.getCoffeeList());
        return "showCoffees";
    }

    @GetMapping("/showUsers")
    public String showUsers(Model model , @AuthenticationPrincipal User user){
        model.addAttribute("user",user);
        model.addAttribute("users",userService.findAll());
        return "showUsers";
    }

    @GetMapping("/showComments/{userId}")
    public String showComments(Model model, @PathVariable int userId , @AuthenticationPrincipal User user){
        model.addAttribute("user",user);
        model.addAttribute("comments",commentService.findAllByUserId(userId));
        return "showComments";
    }

    @GetMapping("/showOrders/{userId}")
    public String showOrders(Model model,@PathVariable int userId , @AuthenticationPrincipal User user){
        model.addAttribute("user",user);
        model.addAttribute("orders",orderService.findAllByUserId(userId));
        return "showOrders";
    }

    @GetMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable int userId){
        userService.deleteById(userId);
        return "redirect:/admin/showUsers";
    }
    @GetMapping("/showOrders/deleteOrder/{orderId}")
    public String deleteOrder(@PathVariable int orderId){
        orderService.deleteById(orderId);
        return "redirect:/admin/showUsers";
    }




}
