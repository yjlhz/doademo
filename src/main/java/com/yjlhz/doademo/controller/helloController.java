package com.yjlhz.doademo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class helloController {

    @GetMapping("/hello")
    public String hello(Model model){
        model.addAttribute("msg","1111111111111");
        return "hello";
    }
}
