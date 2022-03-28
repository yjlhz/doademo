package com.yjlhz.doademo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.CourseForm;
import com.yjlhz.doademo.listener.CourseListener;
import com.yjlhz.doademo.mapper.CourseMapper;
import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.dto.CourseDTO;
import com.yjlhz.doademo.service.CourseService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * @author lhz
 * @title: CourseServiceImpl
 * @projectName doademo
 * @description: 课程信息实现类
 * @date 2022/1/20 23:57
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public ResultVO queryCourseById(Integer courseId) {
        return ResultVOUtil.success(courseMapper.queryCourseById(courseId));
    }

    @Override
    public ResultVO addCourse(CourseForm courseForm) {
        Course course = new Course();
        BeanUtils.copyProperties(courseForm,course);
        int res = courseMapper.addCourse(course);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO updateCourse(CourseForm courseForm) {
        Course course = courseMapper.queryCourseById(courseForm.getCourseId());
        BeanUtils.copyProperties(courseForm,course);
        int res = courseMapper.updateCourseById(course);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO deleteCourse(Integer courseId) {
        int res = courseMapper.deleteCourseById(courseId);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO queryCourseByName(String courseName) {
        Course course = courseMapper.queryCourserByName(courseName);
        if (course == null){
            return ResultVOUtil.error(ResultEnum.NULLPOINT_ERROR);
        }
        return ResultVOUtil.success(course);
    }

    @Override
    public ResultVO queryCourses() {
        List<Course> courseList = courseMapper.queryCourseList();
        return ResultVOUtil.success(courseList);
    }

    @Override
    public ResultVO uploadCourse(MultipartFile multipartFile) {
        try {
            EasyExcel.read(multipartFile.getInputStream(), CourseDTO.class, new CourseListener(courseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVOUtil.success();
    }

    @Override
    public void exportCourse(HttpServletResponse response) {
        try {
            List<Course> dataLists = courseMapper.queryCourseList();
            List<CourseDTO> dataList = new ArrayList<>();
            for (Course course : dataLists){
                CourseDTO courseDTO = new CourseDTO();
                courseDTO.setCourseId(course.getCourseId());
                courseDTO.setCourseName(course.getCourseName());
                courseDTO.setCourseCredit(course.getCourseCredit());
                dataList.add(courseDTO);
            }
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            //设置头居中
            headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            //内容策略
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            //设置 水平居中
            contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
            HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("课程信息表", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), CourseDTO.class).autoCloseStream(Boolean.FALSE).registerWriteHandler(horizontalCellStyleStrategy).sheet("课程信息表").doWrite(dataList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    @Override
//    public ResultVO queryCoursesByCredit(Double CourseCredit) {
//        List<Course> courseList = new ArrayList<>();
//    }

}
