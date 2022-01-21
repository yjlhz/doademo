package com.yjlhz.doademo.service;

import com.yjlhz.doademo.form.CourseForm;
import com.yjlhz.doademo.vo.ResultVO;

public interface CourseService {

    ResultVO addCourse(CourseForm courseForm);

    ResultVO updateCourse(CourseForm courseForm);

    ResultVO deleteCourse(String courseName);

    ResultVO queryCourseByName(String courseName);

    ResultVO queryCourses();

//    ResultVO queryCoursesByCredit(Double CourseCredit);
}
