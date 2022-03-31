package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.StudentForm;
import com.yjlhz.doademo.mapper.*;
import com.yjlhz.doademo.pojo.Plan;
import com.yjlhz.doademo.pojo.PlanCourse;
import com.yjlhz.doademo.pojo.Student;
import com.yjlhz.doademo.pojo.StudentCourse;
import com.yjlhz.doademo.service.StudentService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private PlanMapper planMapper;

    @Autowired
    private PlanCourseMapper planCourseMapper;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private PlanRequirementMapper planRequirementMapper;

    @Override
    public ResultVO queryStudents() {
        List<Student> studentList = studentMapper.queryStudentList();
        return ResultVOUtil.success(studentList);
    }

    @Override
    @Transactional
    public ResultVO addStudent(StudentForm studentForm) {
        if (studentMapper.queryStudentById(studentForm.getSNum())!=null){
            return ResultVOUtil.error(ResultEnum.CONFLICT_ERROR);
        }
        Student student = new Student();
        BeanUtils.copyProperties(studentForm,student);
        //根据学生专业和年级绑定培养计划，培养计划要先存在
        Plan plan = planMapper.queryPlanByMajorAndGrade(studentForm.getGrade(),studentForm.getMajor());
        if (plan == null){
            return ResultVOUtil.error(ResultEnum.NOTHINGNESS_ERROR);
        }
        int res = 1;
        //绑定选课，先根据培养计划查询出来该培养计划绑定的课程
        List<PlanCourse> planCourses = planCourseMapper.queryPlanCourseByPlanId(plan.getId());
        for (PlanCourse planCourse : planCourses){
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setSNum(studentForm.getSNum());
            studentCourse.setCourseId(planCourse.getCourseId());
            res = studentCourseMapper.addStudentCourse(studentCourse);
        }
        //根据培养计划查询指标点，绑定给学生
        List<Integer> requirements = planRequirementMapper.queryRequirementByPlanId(plan.getId());
        StringBuffer str = new StringBuffer();
        int size = requirements.size();
        for (int i = 0;i<size;i++){
            if (i!=size-1){
                str.append("0,");
            }else{
                str.append("0");
            }
        }
        student.setAchieve(str.toString());
        res = studentMapper.addStudent(student);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
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
