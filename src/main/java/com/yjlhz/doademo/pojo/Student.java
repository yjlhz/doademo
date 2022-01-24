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

    private Integer gender;

    private String major;

    private Integer grade;

    private String classes;

    private String phone;

    private String email;

    private String firstAchieve;//以逗号分隔

    private String secondAchieve;

    private static final long serialVersionUID = 1L;

}
