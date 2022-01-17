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

    private String courseName;

    private Double courseCredit;

}
