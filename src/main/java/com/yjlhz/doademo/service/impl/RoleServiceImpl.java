package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.mapper.RoleMapper;
import com.yjlhz.doademo.pojo.Role;
import com.yjlhz.doademo.service.RoleService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lhz
 * @title: RoleServiceImpl
 * @projectName doademo
 * @description: RoleServiceImpl
 * @date 2022/5/7 0:42
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public ResultVO queryRoleList() {
        List<Role> roleList = roleMapper.queryRoleList();
        return ResultVOUtil.success(roleList);
    }

    @Override
    public ResultVO queryRoleById(Integer id) {
        return ResultVOUtil.success(roleMapper.queryRoleById(id));
    }
}
