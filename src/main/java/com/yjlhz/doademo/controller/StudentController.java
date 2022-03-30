package com.yjlhz.doademo.controller;

import com.alibaba.excel.EasyExcel;
import com.yjlhz.doademo.dto.CourseDTO;
import com.yjlhz.doademo.dto.StudentDTO;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.StudentForm;
import com.yjlhz.doademo.listener.CourseListener;
import com.yjlhz.doademo.listener.StudentListener;
import com.yjlhz.doademo.service.StudentService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

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

    @PostMapping("/addStudent")
    ResultVO addStudent(@Valid StudentForm studentForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return studentService.addStudent(studentForm);
    }

    @PostMapping("/uploadStudent")
    @ApiOperation("批量上传学生信息")
    ResultVO uploadCourse(@RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), StudentDTO.class, new StudentListener(studentService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVOUtil.success();
    }

}
