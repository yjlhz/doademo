package com.yjlhz.doademo.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lhz
 * @title: Examine
 * @projectName doademo
 * @description: 考核实体类
 * @date 2022/1/2421:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Examine implements Serializable {

    private Integer id;

    private Integer planCourseId;

    private String description;

    private Double weight;//此处存的权重是对二级指标点的支撑权重

    private static final long serialVersionUID = 1L;

}
