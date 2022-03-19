package com.yjlhz.doademo.service;

import com.yjlhz.doademo.form.FirstRequirementForm;
import com.yjlhz.doademo.form.SecondRequirementForm;
import com.yjlhz.doademo.vo.ResultVO;

/**
 * @author lhz
 * @title: SecondRequirementService
 * @projectName doademo
 * @description: 二级指标点服务层接口
 * @date 2022/3/19 20:38
 */
public interface SecondRequirementService {
    ResultVO querySecondRequirements();

    ResultVO addSecondRequirement(SecondRequirementForm secondRequirementForm);

    ResultVO updateSecondRequirementById(Integer id);

    ResultVO deleteSecondRequirement(Integer id);
}
