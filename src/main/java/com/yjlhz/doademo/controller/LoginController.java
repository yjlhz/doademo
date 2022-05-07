package com.yjlhz.doademo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.pojo.Role;
import com.yjlhz.doademo.pojo.User;
import com.yjlhz.doademo.service.RoleService;
import com.yjlhz.doademo.service.UserService;
import com.yjlhz.doademo.vo.ResultVO;
import com.yjlhz.doademo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lhz
 * @title: LoginController
 * @projectName doademo
 * @description: 登录控制层
 * @date 2022/4/30 10:19
 */

@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

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

    @RequestMapping("/queryUserList")
    public String queryUserList(Model model,
                                @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                                @RequestParam(defaultValue="10",value="pageSize")Integer pageSize){
        //为了程序的严谨性，判断非空：
        //设置默认当前页
        if(pageNum==null || pageNum<=0){
            pageNum = 1;
        }
        //设置默认每页显示的数据数
        if(pageSize == null){
            pageSize = 1;
        }
        System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum,pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<User> users = (List<User>) userService.queryUserList().getData();
            List<UserVO> userList = new ArrayList<>();
            for (User user : users){
                UserVO userVO = new UserVO();
                Role role = (Role) roleService.queryRoleById(user.getRole()).getData();
                userVO.setUserName(user.getUserName());
                userVO.setPassword(user.getPassword());
                userVO.setRole(role.getName());
                userList.add(userVO);
            }
            System.out.println("分页数据："+userList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<UserVO> pageInfo = new PageInfo<UserVO>(userList,pageSize);
            //4.使用model传参数回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("userList",userList);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }
        return "userList";
    }

}
