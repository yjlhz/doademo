package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.FirstRequirementForm;
import com.yjlhz.doademo.mapper.FirstRequirementMapper;
import com.yjlhz.doademo.pojo.FirstRequirement;
import com.yjlhz.doademo.service.FirstRequirementService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lhz
 * @title: FirstRequirementImpl
 * @projectName doademo
 * @description: 一级指标点实现类
 * @date 2022/3/19 16:53
 */

@Service
public class FirstRequirementServiceImpl implements FirstRequirementService {

    @Autowired
    private FirstRequirementMapper firstRequirementMapper;

    @Override
    public ResultVO queryFirstRequirements() {
        List<FirstRequirement> firstRequirementList = firstRequirementMapper.queryFirstRequirementList();
        return ResultVOUtil.success(firstRequirementList);
    }

    @Override
    public ResultVO addFirstRequirement(FirstRequirementForm firstRequirementForm) {
        FirstRequirement firstRequirement = new FirstRequirement();
        BeanUtils.copyProperties(firstRequirementForm,firstRequirement);
        int res = firstRequirementMapper.addFirstRequirement(firstRequirement);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO updateFirstRequirementById(Integer id) {
        return null;
    }

    @Override
    public ResultVO deleteFirstRequirement(Integer id) {
        return null;
    }
}
