package com.yjlhz.doademo.mapper;

import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhz
 * @title: RoleMapper
 * @projectName doademo
 * @description: RoleMapper
 * @date 2022/5/7 0:39
 */

@Mapper
@Repository
public interface RoleMapper {

    List<Role> queryRoleList();

    Role queryRoleById(Integer id);

}
