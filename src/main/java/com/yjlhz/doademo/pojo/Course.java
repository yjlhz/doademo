package com.yjlhz.doademo.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("课程实体类")
public class Course implements Serializable {

    @ApiModelProperty("课程id")
    private Integer courseId;

    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("课程学分")
    private Double courseCredit;

    private static final long serialVersionUID = 1L;

}
