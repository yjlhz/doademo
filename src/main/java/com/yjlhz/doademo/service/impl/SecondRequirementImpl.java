package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.SecondRequirementForm;
import com.yjlhz.doademo.mapper.SecondRequirementMapper;
import com.yjlhz.doademo.pojo.FirstRequirement;
import com.yjlhz.doademo.pojo.SecondRequirement;
import com.yjlhz.doademo.service.SecondRequirementService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lhz
 * @title: SecondRequirementImpl
 * @projectName doademo
 * @description: 二级指标点实现类
 * @date 2022/3/19 20:40
 */
@Service
public class SecondRequirementImpl implements SecondRequirementService {

    @Autowired
    private SecondRequirementMapper secondRequirementMapper;

    @Override
    public ResultVO querySecondRequirements() {
        List<SecondRequirement> secondRequirementList = secondRequirementMapper.querySecondRequirementList();
        return ResultVOUtil.success(secondRequirementList);
    }

    @Override
    public ResultVO addSecondRequirement(SecondRequirementForm secondRequirementForm) {
        SecondRequirement secondRequirement = new SecondRequirement();
        BeanUtils.copyProperties(secondRequirementForm,secondRequirement);
        int res = secondRequirementMapper.addSecondRequirement(secondRequirement);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO updateSecondRequirementById(Integer id) {
        return null;
    }

    @Override
    public ResultVO deleteSecondRequirement(Integer id) {
        return null;
    }
}
