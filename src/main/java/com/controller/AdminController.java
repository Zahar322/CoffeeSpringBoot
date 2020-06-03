package com.controller;

import com.controller.entity.Coffee;
import com.controller.entity.User;
import com.controller.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.Valid;

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
        model.addAttribute("userId",userId);
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

    @GetMapping("/addCoffee")
    public String addCoffee( Model model,@AuthenticationPrincipal User user){
        model.addAttribute("user",user);
        model.addAttribute("coffeeValid",new Coffee());
        return "addCoffee";
    }
//    @PostMapping("/addCoffee")
//    public String addCoffee(@ModelAttribute ("coffeeValid") @Valid Coffee coffee, BindingResult result,@RequestParam(name = "image") CommonsMultipartFile image){
//        if(result.hasErrors()){
//            return "redirect:/admin/addCoffee";
//        }
//        coffee.setImage(image.getBytes());
//        coffeeService.save(coffee);
//        return "redirect:/makeOrder";
//    }

    @PostMapping("/addCoffee")
    public String addCoffee(@ModelAttribute  Coffee coffee,@RequestParam(name = "myimage") CommonsMultipartFile image){
        coffee.setImage(image.getBytes());
        coffeeService.save(coffee);
        return "redirect:/makeOrder";
    }

    @GetMapping("/updateCoffee/{id}")
    public String update(@PathVariable int id,Model model,@AuthenticationPrincipal User user){
        model.addAttribute("user",user);
        model.addAttribute("coffee",coffeeService.findById(id));
        return "updateCoffee";
    }

    @PostMapping("/updateCoffee")
    public String update(@ModelAttribute Coffee coffee,@RequestParam(name = "myimage") CommonsMultipartFile image ){
        coffee.setImage(image.getBytes());
        coffeeService.update(coffee);
        return "redirect:/makeOrder";
    }




}
