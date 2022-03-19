package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.form.StudentForm;
import com.yjlhz.doademo.mapper.StudentMapper;
import com.yjlhz.doademo.pojo.Student;
import com.yjlhz.doademo.service.StudentService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lhz
 * @title: StudentServiceImpl
 * @projectName doademo
 * @description: 学生信息实现类
 * @date 2022/3/19 15:15
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public ResultVO queryStudents() {
        List<Student> studentList = studentMapper.queryStudentList();
        return ResultVOUtil.success(studentList);
    }

    @Override
    public ResultVO addStudent(StudentForm studentForm) {
        return null;
    }

    @Override
    public ResultVO updateStudent(StudentForm studentForm) {
        return null;
    }

    @Override
    public ResultVO deleteStudent(String sNum) {
        return null;
    }

    @Override
    public ResultVO queryStudentByNum(String sNum) {
        return null;
    }
}
