package com.yjlhz.doademo.form;

import lombok.Data;

/**
 * @author lhz
 * @title: StudentForm
 * @projectName doademo
 * @description: 学生信息提交表单
 * @date 2022/3/19 15:20
 */

@Data
public class StudentForm {

    private String sNum;

    private String sName;

    private Integer gender;

    private String major;

    private Integer grade;

    private String classes;

    private String phone;

    private String email;

}
