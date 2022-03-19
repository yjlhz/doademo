package com.yjlhz.doademo.service;

import com.yjlhz.doademo.form.FirstRequirementForm;
import com.yjlhz.doademo.vo.ResultVO;

/**
 * @author lhz
 * @title: FirstRequirementService
 * @projectName doademo
 * @description: 一级指标点服务层接口
 * @date 2022/3/19 16:50
 */
public interface FirstRequirementService {

    ResultVO queryFirstRequirements();

    ResultVO addFirstRequirement(FirstRequirementForm firstRequirementForm);

    ResultVO updateFirstRequirementById(Integer id);

    ResultVO deleteFirstRequirement(Integer id);

}
