package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.pojo.User;
import com.yjlhz.doademo.service.UserService;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author lhz
 * @title: LoginController
 * @projectName doademo
 * @description: 登录控制层
 * @date 2022/4/30 10:19
 */

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
                        Model model, HttpSession session){
        ResultVO info = userService.Login(username, password);
        if (info.getCode() == 5 || info.getCode() == 6){
            model.addAttribute("msg",info.getMsg());
            return "index";
        }
        User user = (User) userService.queryUserByName(username).getData();
        session.setAttribute("role",user.getRole());
        session.setAttribute("loginUser",username);
        return "redirect:/main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/index";
    }

}
