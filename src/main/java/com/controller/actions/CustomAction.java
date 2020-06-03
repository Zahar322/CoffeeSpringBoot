package com.controller.actions;

import com.controller.entity.Coffee;
import com.controller.entity.User;
import com.controller.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.webflow.action.MultiAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.util.List;

@Component
public class CustomAction extends  MultiAction {

    @Autowired
    private CoffeeService coffeeService;

    public Event action(RequestContext context){
        Event event=context.getCurrentEvent();
        Event event1=new Event(event,"failure");

        System.out.println(event);
        context.getFlowScope().put("coffees",coffeeService.findAll());
        return null;
    }

    public Event success(RequestContext context){
        List<Coffee> coffees=(List<Coffee>)context.getFlowScope().get("coffees");
        //System.out.println(coffees.get(0));
        //System.out.println((List<Coffee>)context.getFlowScope().get("coffees"));
        return null;
    }

    public Event custom(){

        return success();
    }
}
