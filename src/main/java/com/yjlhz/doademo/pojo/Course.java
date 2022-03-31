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

    @ApiModelProperty("课程支撑指标点")
    private String requirementNo;//存储课程应该支撑的指标点，以逗号分隔

    private static final long serialVersionUID = 1L;

}
