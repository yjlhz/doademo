package com.yjlhz.doademo.service;

import com.yjlhz.doademo.vo.ResultVO;

/**
 * @author lhz
 * @title: RoleService
 * @projectName doademo
 * @description: RoleService
 * @date 2022/5/7 0:40
 */
public interface RoleService {

    ResultVO queryRoleList();

    ResultVO queryRoleById(Integer id);

}
