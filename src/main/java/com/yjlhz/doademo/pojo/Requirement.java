package com.yjlhz.doademo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lhz
 * @title: FirstRequirement
 * @projectName doademo
 * @description: 一级指标点实体类
 * @date 2022/1/2421:06
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Requirement implements Serializable {

    private Integer id;

    private Integer requirementNo;

    private String description;

    private static final long serialVersionUID = 1L;

}
