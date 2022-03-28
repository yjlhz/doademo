package com.yjlhz.doademo.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lhz
 * @title: CourseForm
 * @projectName doademo
 * @description: 用户提交的课程信息
 * @date 2022/1/2023:54
 */

@Data
public class CourseForm {

    private Integer courseId;

    private String courseName;

    private Double courseCredit;
}
