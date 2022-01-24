package com.yjlhz.doademo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lhz
 * @title: StudentCourse
 * @projectName doademo
 * @description: 学生选课
 * @date 2022/1/2421:36
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourse implements Serializable {

    private Integer id;

    private String sNum;

    private Integer courseId;

    private static final long serialVersionUID = 1L;

}
