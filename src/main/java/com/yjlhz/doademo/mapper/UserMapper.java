package com.yjlhz.doademo.mapper;

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

}
