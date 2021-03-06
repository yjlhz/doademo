package com.yjlhz.doademo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjlhz.doademo.form.CourseForm;
import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.pojo.Requirement;
import com.yjlhz.doademo.pojo.Role;
import com.yjlhz.doademo.pojo.User;
import com.yjlhz.doademo.service.RoleService;
import com.yjlhz.doademo.service.UserService;
import com.yjlhz.doademo.vo.ResultVO;
import com.yjlhz.doademo.vo.UserVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
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

    @GetMapping("/toAdd")
    public String toAdd(Model model){
        List<Role> roleList = (List<Role>) roleService.queryRoleList().getData();
        model.addAttribute("roleList",roleList);
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(User user,Model model){
        ResultVO resultVO = userService.addUser(user);
        if (resultVO.getCode() == 7){
            List<Role> roleList = (List<Role>) roleService.queryRoleList().getData();
            model.addAttribute("roleList",roleList);
            model.addAttribute("msg",resultVO.getMsg());
            return "addUser";
        }
        userService.addUser(user);
        return "redirect:/user/queryUserList";
    }

    @GetMapping("/toUpdate/{name}")
    public String toUpdate(@PathVariable("name")String name,Model model){
        User user = (User) userService.queryUserByName(name).getData();
        List<Role> roleList = (List<Role>) roleService.queryRoleList().getData();
        model.addAttribute("user",user);
        model.addAttribute("roleList",roleList);
        return "updateUser";
    }

    @PostMapping("/updateUser")
    public String updateUserByName(User user){
        userService.updateUser(user);
        return "redirect:/user/queryUserList";
    }

    @GetMapping("/deleteUser/{name}")
    public String deleteCourse(@PathVariable String name){
        userService.deleteUser(name);
        return "redirect:/user/queryUserList";
    }

    @GetMapping("/export")
    void exportCourse(HttpServletResponse response){
        userService.exportUser(response);
    }

    @GetMapping("/download")
    void download(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取model路径
            String realPath = "C:/Users/Lenovo/Desktop/doademo/src/main/resources"+ File.separator + "userTemplate.xlsx";

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(realPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            //清空response
            response.reset();
            //设置response响应头
            response.setCharacterEncoding("UTF-8");
            response.setContentType("multipart/form-data");
            response.setContentType("application/x-download");
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Disposition","attachment;fileName="+ URLEncoder.encode(URLEncoder.encode("userTemplate.xlsx","UTF-8"),"ISO-8859-1"));

            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/upload")
    String uploadUser(@RequestParam(value = "file", required = false) MultipartFile file) {
        userService.uploadUser(file);
        return "redirect:/user/queryUserList";
    }

    @GetMapping("/toUpload")
    public String toUpload(){
        return "uploadUser";
    }

    @GetMapping("/toChange")
    public String toChange(HttpSession session,Model model){
        String userName = (String) session.getAttribute("loginUser");
        User user = (User) userService.queryUserByName(userName).getData();
        model.addAttribute("user",user);
        return "change";
    }

    @PostMapping("/change")
    public String change(User user,Model model){
        ResultVO resultVO = userService.updateUser(user);
        model.addAttribute("msg",resultVO.getMsg());
        return "changeSusses";
    }

    @GetMapping("downloadHelper")
    void downloadHelper(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取model路径
            String realPath = "C:/Users/Lenovo/Desktop/doademo/src/main/resources"+ File.separator + "用户手册.docx";

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(realPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            //清空response
            response.reset();
            //设置response响应头
            response.setCharacterEncoding("UTF-8");
            response.setContentType("multipart/form-data");
            response.setContentType("application/x-download");
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Disposition","attachment;fileName="+ URLEncoder.encode(URLEncoder.encode("用户手册.docx","UTF-8"),"ISO-8859-1"));

            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
