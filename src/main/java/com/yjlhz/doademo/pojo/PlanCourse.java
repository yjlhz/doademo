package com.yjlhz.doademo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lhz
 * @title: PlanCourse
 * @projectName doademo
 * @description: 对教学计划指标点起支撑的课程
 * @date 2022/1/2421:17
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanCourse implements Serializable {

    private Integer id;

    private Integer planId;

    private Integer courseId;

    private Double weight;//此处存课程占培养计划的权重（按学分所占最低毕业要求学分计算）

    private static final long serialVersionUID = 1L;

}
