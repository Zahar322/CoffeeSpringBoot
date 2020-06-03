package com.controller;



import com.controller.entity.*;

import com.controller.export.ExcelOrderReportView;
import com.controller.export.PdfOrderReportView;
import com.controller.service.*;
import com.controller.vk.VkInitializer;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
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

   @Autowired
   private CommentService commentService;

   @Autowired
   private MessageService messageService;

   Logger logger= LoggerFactory.getLogger(ApplicationController.class);

    private void convertImage(HttpServletResponse res, AbstractImage entity){
        res.setContentType("image/jpeg");
        try {
            ServletOutputStream outputStream=res.getOutputStream();
            byte [] image=entity.getImage();
            if(image!=null) {
                outputStream.write(image);
                outputStream.close();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @GetMapping("/report/{orderId}")
    public ModelAndView report(HttpServletRequest request,HttpServletResponse response,@PathVariable int orderId){
        List<OrderItem> items=orderItemService.findAllByOrderId(orderId);
        String type=request.getParameter("type");
        if(type.equals("xls")){
            return new ModelAndView(new ExcelOrderReportView(),"orderItems",items);
        }
        return new ModelAndView(new PdfOrderReportView(),"orderItems",items);
    }


    @GetMapping("/test/{id}")
    public String test(@PathVariable int id,Model model){
        model.addAttribute("user",userService.findById(id));
        return "fragment/test";
    }

    @GetMapping("/air")
    public String air(@RequestParam(required = false)String path){
        return path;
    }

    @GetMapping("/user/{id}")
    public String userImage(HttpServletResponse res, @PathVariable int id){
        User user =userService.findById(id);
        convertImage(res,user);
        return null;

    }

    @GetMapping("/image/{id}")
    public String welcome(HttpServletResponse res, @PathVariable int id){
        Coffee coffee =coffeeService.findById(id);
        convertImage(res,coffee);
        return null;

    }


    @GetMapping("/welcome")
    public String welcome(Model model){
        model.addAttribute("users",userService.findAll());
        User firstUser = userService.findById(1);
        messageService.sendMessage(firstUser.getUsername(), firstUser.getPassword(), firstUser);
        return "welcome";
    }

    @GetMapping("/main")
    public String main(Model model, HttpServletRequest request) throws ClientException, ApiException {
        logger.error("running");
        model.addAttribute("userReg",new User());
        String code = request.getParameter("code");
        if (code != null) {
            VkInitializer vkInitializer = new VkInitializer();
            vkInitializer.authorizate(code, model);
            return "vk";
        }
        return "main";
    }

    @PostMapping("/reg")
    public String main(@ModelAttribute("userReg") @Valid User user, BindingResult result){
        if(result.hasErrors()){
            return "/main";
        }
        user.setRole("USER_ROLE");
        messageService.sendMessage(user.getUsername(), user.getPassword(), user);
        userService.save(user);
        return "redirect:/main";

    }

    @GetMapping("/makeOrder")
    public String makeOrder(Model model, @AuthenticationPrincipal User user){
        List<Coffee> coffeeList=coffeeService.findAll();
        model.addAttribute("user",user);
        model.addAttribute("coffees",coffeeList);
        model.addAttribute("order",new Order());
        messageService.sendMessage(user.getUsername(), user.getPassword(), user);
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
        Coffee [] coffees=getCoffees(coffeeIds.toArray(new Integer[coffeeIds.size()]),coffeeService.findAll());
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
        model.addAttribute("orderId",orderId);
        model.addAttribute("user",user);
        model.addAttribute("orderId",orderId);
        model.addAttribute("orderItems",orderItemService.findAllByOrderId(orderId));
        return "orderItems";
    }

    @GetMapping("/coffee/{id}")
    public String coffee(@PathVariable int id,Model model,@AuthenticationPrincipal User user){
        model.addAttribute("coffee",coffeeService.findById(id));
        model.addAttribute("user",user);
        model.addAttribute("comments",commentService.findAllByCoffeeIdOrderByIdDesc(id));
        return "coffee";
    }

    @GetMapping("/personalArea")
    public String personalArea(Model model,@AuthenticationPrincipal User user){
        model.addAttribute("user",user);
        if(user.getRole().equals("ROLE_ADMIN")){
            model.addAttribute("users",userService.findAll());
        }
        return "personalArea";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute User user,@AuthenticationPrincipal User oldUser){
        user.prepareUserForUpdate(oldUser);
        userService.update(user);
        commentService.updateUserComments(user);
        return "redirect:/personalArea";
    }


    private int getTotalPrice(Coffee[] coffees, Integer [] amount){
        int totalPrice=0;
        for(int i=0;i<coffees.length;i++){
            totalPrice=totalPrice+Math.abs(coffees[i].getPrice()*amount[i]);
        }
        return totalPrice;
    }

    private Coffee [] getCoffees(Integer [] ids,List<Coffee> coffeeList){
        Map<Integer,Coffee> map=init(coffeeList);
        Coffee [] coffees=new Coffee[ids.length];
        for (int i=0;i<ids.length;i++){
            coffees[i]=map.get(ids[i]);
        }
        return coffees;
    }

    private Map<Integer,Coffee> init(List<Coffee> coffeeList){
        Map<Integer,Coffee> coffeePrices=new HashMap<>();
        for(Coffee coffee:coffeeList){
            coffeePrices.put(coffee.getId(),coffee);
        }
        return coffeePrices;
    }
}
