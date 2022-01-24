package com.yjlhz.doademo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lhz
 * @title: PlanRequirement
 * @projectName doademo
 * @description: 教学计划指定一级指标点
 * @date 2022/1/2421:20
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequirement implements Serializable {

    private Integer id;

    private Integer planId;

    private Integer requirementNo;

    private static final long serialVersionUID = 1L;

}
