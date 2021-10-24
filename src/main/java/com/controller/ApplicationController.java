package com.controller;

import com.controller.entity.Order;
import com.controller.entity.User;

import com.controller.response.CoffeeResponse;
import com.controller.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
public class ApplicationController {

   @Autowired
   private UserService userService;
   @Autowired
   private CoffeeService coffeeService;
   @Autowired
   private OrderService orderService;
   @Autowired
   private OrderItemService orderItemService;
   @Autowired
   private CommentService commentService;
   @Autowired
   private MediaService mediaService;

   @GetMapping("/")
   public String content() {
       return "content";
   }

    @GetMapping("/conf")
    public String conf() {
        return "conf";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/share")
    public String share() {
        return "screenshare";
    }

    @GetMapping("/welcome")
    public String welcome(Model model){
        model.addAttribute("users",userService.findAll());
        return "welcome";
    }

    @GetMapping("/main")
    public String main(Model model){

        model.addAttribute("userReg",new User());
        return "main";
    }

//    @PostMapping("/reg")
//    public String main(@ModelAttribute("userReg") @Valid User user, BindingResult result){
//        if(result.hasErrors()){
//            return "/main";
//        }
//        user.setRole("USER_ROLE");
//        userService.save(user);
//        return "redirect:/main";
//
//    }

    @PostMapping("/reg")
    public String main(@ModelAttribute("userReg")  User user) throws IOException {
        userService.save(user, null, null);
        return "redirect:/main";

    }

    @GetMapping("/makeOrder")
    public String makeOrder(Model model, @AuthenticationPrincipal User user){

        model.addAttribute("user",user);
        model.addAttribute("coffees", coffeeService.findAll());
        model.addAttribute("order",new Order());
        return "makeOrder";
    }


    @PostMapping("/makeOrder")
    public String makeOrder(@ModelAttribute @Valid Order order,
                            BindingResult result,
                            @RequestParam(value = "count",required = false)  List<Integer> amounts,
                            @RequestParam(value = "coffeeId",required = false)  List<Integer> coffeeIds,
                            @AuthenticationPrincipal User user
                            ){
        if(result.hasErrors()){


            return "makeOrder";
        }
        order.setUserId(user.getId());
        int totalPrice = coffeeService.getTotalPrice(coffeeIds, amounts);
        order.setTotalPrice(totalPrice);
        order.setCreationDate(new Timestamp(new Date().getTime()));
        orderService.save(order);
        int orderId=orderService.findLastOrderId();
        orderItemService.saveAll(amounts,coffeeIds,orderId);

        return "redirect:/orderList";
    }

    @GetMapping("/orderList")
    public String orderList( Model model,@AuthenticationPrincipal User user){
        model.addAttribute("user",user);
        model.addAttribute("orders",orderService.findAllByUserId(user.getId()));
        return "orderList";
    }

    @GetMapping("/orderItems/{orderId}")
    public String orderItemList(@PathVariable int orderId, Model model,@AuthenticationPrincipal User user){
        model.addAttribute("user",user);
        model.addAttribute("orderId",orderId);
        model.addAttribute("orderItems",orderItemService.findAllByOrderId(orderId));
        return "orderItems";
    }

    @GetMapping("/coffee/{id}")
    public String coffee(@PathVariable int id,Model model,@AuthenticationPrincipal User user){
        model.addAttribute("coffee",coffeeService.findById(id));
        model.addAttribute("user",user);
        model.addAttribute("comments",commentService.findAllByCoffeeId(id));
        return "coffee";
    }

    @GetMapping("/personalArea")
    public String personalArea(Model model,@AuthenticationPrincipal User user){
        model.addAttribute("user",user);
        return "personalArea";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute User user,@AuthenticationPrincipal User oldUser){
        user.prepareUserForUpdate(oldUser);

        userService.update(user);
        commentService.updateUserComments(user);
        return "redirect:/personalArea";
    }

    @GetMapping("/image/{path}/{type}")
    public ResponseEntity<Resource> getFile(@PathVariable String path, @PathVariable String type) throws IOException {
        byte[] file = mediaService.getFile(path);
        return ResponseEntity.ok()
                             .contentType(MediaType.parseMediaType(type.replaceAll("9", "/")))
                             .body(new ByteArrayResource(file));
    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> users() {
        return userService.findAll();
    }

    @GetMapping("/coffees")
    @ResponseBody
    public CoffeeResponse coffeeResponse() {
        return new CoffeeResponse(coffeeService.findAll());
    }
}
