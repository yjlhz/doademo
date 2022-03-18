package com.yjlhz.doademo.service;

import com.yjlhz.doademo.form.CourseForm;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

public interface CourseService {

    ResultVO addCourse(CourseForm courseForm);

    ResultVO updateCourse(CourseForm courseForm);

    ResultVO deleteCourse(String courseName);

    ResultVO queryCourseByName(String courseName);

    ResultVO queryCourses();

    /**
     * 通过文件新增课程
     * @param multipartFile
     * @return
     */
    ResultVO uploadCourse(MultipartFile multipartFile);

//    ResultVO queryCoursesByCredit(Double CourseCredit);
}
