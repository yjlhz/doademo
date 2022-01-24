package com.yjlhz.doademo.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lhz
 * @title: CourseScore
 * @projectName doademo
 * @description: 学生课程得分
 * @date 2022/1/2420:59
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("学生课程得分实体类")
public class CourseScore implements Serializable {

    @ApiModelProperty("学号")
    private String sNum;

    @ApiModelProperty("课程id")
    private Integer courseId;

    @ApiModelProperty("课程得分")
    private Double score;

    private static final long serialVersionUID = 1L;
}
