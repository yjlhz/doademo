package com.yjlhz.doademo.service;

import com.yjlhz.doademo.form.StudentForm;
import com.yjlhz.doademo.vo.ResultVO;

/**
 * @author lhz
 * @title: StudentService
 * @projectName doademo
 * @description: 学生类业务层接口
 * @date 2022/3/19 15:14
 */
public interface StudentService {

    ResultVO queryStudents();

    ResultVO addStudent(StudentForm studentForm);

    ResultVO updateStudent(StudentForm studentForm);

    ResultVO deleteStudent(String sNum);

    ResultVO queryStudentByNum(String sNum);

    ResultVO queryStudentByPlan(Integer grade,String major);

}
