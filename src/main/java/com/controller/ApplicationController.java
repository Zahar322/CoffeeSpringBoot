package com.controller;



import com.controller.entity.Coffee;
import com.controller.entity.Order;
import com.controller.entity.User;

import com.controller.service.CoffeeService;
import com.controller.service.OrderItemService;
import com.controller.service.OrderService;
import com.controller.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/reg")
    public String main(@ModelAttribute("userReg") @Valid User user, BindingResult result){
        if(result.hasErrors()){
            return "/main";
        }
        user.setRole("USER_ROLE");
        userService.save(user);
        return "redirect:/main";

    }

    @GetMapping("/makeOrder")
    public String makeOrder(Model model, @AuthenticationPrincipal User user){

        model.addAttribute("user",user);
        model.addAttribute("coffees",coffeeService.getCoffeeList());
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


        Coffee [] coffees=getCoffees(coffeeIds.toArray(new Integer[coffeeIds.size()]));
        int totalPrice=getTotalPrice(coffees,amounts.toArray(new Integer[amounts.size()]));

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





    private int getTotalPrice(Coffee[] coffees, Integer [] amount){
        int totalPrice=0;
        for(int i=0;i<coffees.length;i++){
            totalPrice=totalPrice+Math.abs(coffees[i].getPrice()*amount[i]);
        }
        return totalPrice;
    }

    private Coffee [] getCoffees(Integer [] ids){
        Map<Integer,Coffee> map=coffeeService.getCoffeePrices();
        Coffee [] coffees=new Coffee[ids.length];
        for (int i=0;i<ids.length;i++){
            coffees[i]=map.get(ids[i]);
        }
        return coffees;
    }
}
