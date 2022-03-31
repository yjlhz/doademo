package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.ObjectiveForm;
import com.yjlhz.doademo.mapper.ObjectiveMapper;
import com.yjlhz.doademo.pojo.Objective;
import com.yjlhz.doademo.service.ObjectiveService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lhz
 * @title: ObjectiveServiceImpl
 * @projectName doademo
 * @description: 教学目标实现类
 * @date 2022/3/31 19:42
 */

@Service
public class ObjectiveServiceImpl implements ObjectiveService {

    @Autowired
    private ObjectiveMapper objectiveMapper;

    @Override
    public ResultVO queryByPlanIdAndCourseId(Integer planId, Integer courseId) {
        return ResultVOUtil.success(objectiveMapper.queryByPlanIdAndCourseId(planId,courseId));
    }

    @Override
    public ResultVO addObjective(ObjectiveForm objectiveForm) {
        Objective objective = new Objective();
        BeanUtils.copyProperties(objectiveForm,objective);
        objective.setAchieve(0.0);
        int res = objectiveMapper.addObjective(objective);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO updateObjective(ObjectiveForm objectiveForm,Integer id) {
        Objective objective = objectiveMapper.queryById(id);
        objective.setDescription(objectiveForm.getDescription());
        objective.setObjectiveNo(objectiveForm.getObjectiveNo());
        objective.setRequirementNo(objectiveForm.getRequirementNo());
        int res = objectiveMapper.updateObjective(objective);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO deleteObjective(Integer id) {
        int res = objectiveMapper.deleteObjectiveById(id);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }
}
