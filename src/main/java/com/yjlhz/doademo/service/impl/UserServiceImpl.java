package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.mapper.UserMapper;
import com.yjlhz.doademo.pojo.User;
import com.yjlhz.doademo.service.UserService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lhz
 * @title: UserServiceImpl
 * @projectName doademo
 * @description: user实现类
 * @date 2022/4/30 10:41
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultVO Login(String name,String password) {
        User user = userMapper.queryByName(name);
        if (user == null){
            return ResultVOUtil.error(ResultEnum.USER_DOESNOT_EXIST);
        }
        if (!password.equals(user.getPassword())){
            return ResultVOUtil.error(ResultEnum.PASSWORD_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO queryUserByName(String name) {
        User user = userMapper.queryByName(name);
        return ResultVOUtil.success(user);
    }
}
