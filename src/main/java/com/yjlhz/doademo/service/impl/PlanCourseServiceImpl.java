package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.mapper.CourseMapper;
import com.yjlhz.doademo.mapper.PlanCourseMapper;
import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.pojo.PlanCourse;
import com.yjlhz.doademo.service.PlanCourseService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lhz
 * @title: PlanCourseServiceImpl
 * @projectName doademo
 * @description: planCourse实现类
 * @date 2022/4/3 16:34
 */

@Service
public class PlanCourseServiceImpl implements PlanCourseService {

    @Autowired
    private PlanCourseMapper planCourseMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public ResultVO queryPlanCourseByPlanId(Integer planId) {
        List<PlanCourse> planCourseList = planCourseMapper.queryPlanCourseByPlanId(planId);
        return ResultVOUtil.success(planCourseList);
    }

    @Override
    public ResultVO addPlanCourse(PlanCourse planCourse) {
        int res = planCourseMapper.addPlanCourse(planCourse);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO deletePlanCourseByPlanId(Integer planId) {
        int res = planCourseMapper.deletePlanCourseByPlanId(planId);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO queryCourseByPlanId(Integer planId) {
        List<PlanCourse> planCourses = planCourseMapper.queryPlanCourseByPlanId(planId);
        List<Course> courseList = new ArrayList<>();
        for (PlanCourse planCourse : planCourses){
            Course course = courseMapper.queryCourseById(planCourse.getCourseId());
            courseList.add(course);
        }
        return ResultVOUtil.success(courseList);
    }
}
