package com.yjlhz.doademo.controller;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjlhz.doademo.dto.CourseDTO;
import com.yjlhz.doademo.dto.StudentDTO;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.CourseForm;
import com.yjlhz.doademo.form.StudentForm;
import com.yjlhz.doademo.listener.CourseListener;
import com.yjlhz.doademo.listener.StudentListener;
import com.yjlhz.doademo.pojo.*;
import com.yjlhz.doademo.service.StudentCourseService;
import com.yjlhz.doademo.service.StudentProblemService;
import com.yjlhz.doademo.service.StudentService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author lhz
 * @title: StudentController
 * @projectName doademo
 * @description: 学生信息控制层
 * @date 2022/3/19 15:29
 */

@Controller
@Slf4j
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private StudentProblemService studentProblemService;

    @GetMapping("/queryStudentList")
    public String queryStudentList(Model model,
                            @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                            @RequestParam(defaultValue="10",value="pageSize")Integer pageSize){
        //为了程序的严谨性，判断非空：
        //设置默认当前页
        if(pageNum==null || pageNum<=0){
            pageNum = 1;
        }
        //设置默认每页显示的数据数
        if(pageSize == null){
            pageSize = 1;
        }
        System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum,pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Student> studentList = (List<Student>) studentService.queryStudents().getData();
            System.out.println("分页数据："+studentList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Student> pageInfo = new PageInfo<Student>(studentList,pageSize);
            //4.使用model传参数回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("studentList",studentList);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }
        return "studentList";
    }

    @GetMapping("/download")
    void download(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取model路径
            String realPath = "C:/Users/Lenovo/Desktop/doademo/src/main/resources"+ File.separator + "studentTemplate.xlsx";

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(realPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            //清空response
            response.reset();
            //设置response响应头
            response.setCharacterEncoding("UTF-8");
            response.setContentType("multipart/form-data");
            response.setContentType("application/x-download");
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Disposition","attachment;fileName="+ URLEncoder.encode(URLEncoder.encode("studentTemplate.xlsx","UTF-8"),"ISO-8859-1"));

            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/addStudent")
    String addStudent(StudentForm studentForm,Model model){
        ResultVO resultVO = studentService.addStudent(studentForm);
        if (resultVO.getCode() == 4){
            model.addAttribute("msg",resultVO.getMsg());
            return "notExistPlan";
        }
        return "redirect:/student/queryStudentList";
    }

    @GetMapping("/toAdd")
    public String toAdd(){
        return "addStudent";
    }

    @PostMapping("/uploadStudent")
    @ApiOperation("批量上传学生信息")
    String uploadStudent(@RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), StudentDTO.class, new StudentListener(studentService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/student/queryStudentList";
    }

    @GetMapping("/toUpload")
    public String toUpload(){
        return "uploadStudent";
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id") String id){
        studentService.deleteStudent(id);
        studentCourseService.deleteBysNum(id);
        studentProblemService.deleteBysNum(id);
        return "redirect:/student/queryStudentList";
    }

    @PostMapping("/updateStudent")
    public String updateStudentById(StudentForm studentForm){
        studentService.updateStudent(studentForm);
        return "redirect:/student/queryStudentList";
    }

    @GetMapping("/toUpdate/{sNum}")
    public String toUpdate(@PathVariable("sNum")String sNum,Model model){
        Student student = (Student) studentService.queryStudentByNum(sNum).getData();
        model.addAttribute("student",student);
        return "updateStudent";
    }

}
