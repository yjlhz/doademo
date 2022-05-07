package com.yjlhz.doademo.service;

import com.yjlhz.doademo.pojo.User;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lhz
 * @title: UserService
 * @projectName doademo
 * @description: 登录
 * @date 2022/4/30 10:39
 */
public interface UserService {

    ResultVO Login(String name,String password);

    ResultVO queryUserByName(String name);

    ResultVO queryUserList();

    ResultVO addUser(User user);

    ResultVO deleteUser(String name);

    ResultVO updateUser(User user);

    ResultVO uploadUser(MultipartFile multipartFile);

    void exportUser(HttpServletResponse response);

}
