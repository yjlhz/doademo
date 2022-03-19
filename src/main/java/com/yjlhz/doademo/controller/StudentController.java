package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.service.StudentService;
import com.yjlhz.doademo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lhz
 * @title: StudentController
 * @projectName doademo
 * @description: 学生信息控制层
 * @date 2022/3/19 15:29
 */

@RestController
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/queryStudentList")
    ResultVO queryStudentList(){
        return studentService.queryStudents();
    }

}
