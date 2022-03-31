package com.yjlhz.doademo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lhz
 * @title: Student
 * @projectName doademo
 * @description: 学生实体类
 * @date 2022/1/2421:32
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    private String sNum;

    private String sName;

    private Integer gender;//0:女 1:男

    private String major;

    private Integer grade;

    private String classes;

    private String phone;

    private String email;

    private String achieve;//以逗号分隔

    private static final long serialVersionUID = 1L;

}
