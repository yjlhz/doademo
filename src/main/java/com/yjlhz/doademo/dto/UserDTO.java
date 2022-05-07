package com.yjlhz.doademo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lhz
 * @title: UserDTO
 * @projectName doademo
 * @description: UserDTO
 * @date 2022/5/7 16:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @ExcelProperty(value = "用户名称",index = 0)
    private String userName;

    @ExcelProperty(value = "用户密码",index = 1)
    private String password;

    @ExcelProperty(value = "用户角色",index = 2)
    private Integer role;

}
