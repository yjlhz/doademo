package com.yjlhz.doademo.service;

import com.yjlhz.doademo.form.ExamineForm;
import com.yjlhz.doademo.vo.ResultVO;

/**
 * @author lhz
 * @title: ExamineService
 * @projectName doademo
 * @description: 考核接口
 * @date 2022/3/20 0:41
 */
public interface ExamineService {

    ResultVO queryExaminesByPlanCourseId(Integer planId,Integer courseId);

    ResultVO addExamine(ExamineForm examineForm);

}
