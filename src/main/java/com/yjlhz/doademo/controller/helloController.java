package com.yjlhz.doademo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class helloController {

    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("msg","1111111111111");
        return "hello";
    }
}
