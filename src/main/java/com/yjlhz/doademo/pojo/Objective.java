package com.yjlhz.doademo.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lhz
 * @title: Target
 * @projectName doademo
 * @description: 课程目标实体类
 * @date 2022/3/22 23:20
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("课程目标实体类")
public class Objective implements Serializable {

    private Integer objectiveId;

    private Integer objectiveNo;

    private Integer planCourseId;

    private String description;

    private Integer requirementNo;

    private Double achieve;

    private static final long serialVersionUID = 1L;

}
