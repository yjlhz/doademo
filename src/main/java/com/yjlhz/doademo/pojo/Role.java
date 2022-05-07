package com.yjlhz.doademo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lhz
 * @title: Role
 * @projectName doademo
 * @description: 用户角色
 * @date 2022/5/7 0:37
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

    private Integer id;

    private String name;

    private static final long serialVersionUID = 1L;

}
