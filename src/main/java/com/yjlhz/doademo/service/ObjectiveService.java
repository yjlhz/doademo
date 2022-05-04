package com.yjlhz.doademo.service;

import com.yjlhz.doademo.form.ObjectiveForm;
import com.yjlhz.doademo.vo.ResultVO;
import io.swagger.models.auth.In;

/**
 * @author lhz
 * @title: ObjectiveService
 * @projectName doademo
 * @description: 教学目标服务层接口
 * @date 2022/3/31 19:40
 */
public interface ObjectiveService {

    ResultVO queryByPlanIdAndCourseId(Integer planId,Integer courseId);

    ResultVO addObjective(ObjectiveForm objectiveForm);

    ResultVO updateObjective(ObjectiveForm objectiveForm,Integer id);

    ResultVO deleteObjective(Integer id);

    ResultVO queryById(Integer Id);

    ResultVO queryObjectives();

}
