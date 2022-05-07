package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.dto.CourseDTO;
import com.yjlhz.doademo.dto.UserDTO;
import com.yjlhz.doademo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhz
 * @title: UserMapper
 * @projectName doademo
 * @description: userDao
 * @date 2022/4/30 10:42
 */

@Mapper
@Repository
public interface UserMapper {

    User queryByName(String name);

    List<User> queryUserList();

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(String name);

    int upload(UserDTO record);

}
