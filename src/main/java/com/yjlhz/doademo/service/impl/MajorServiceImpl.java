package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.MajorForm;
import com.yjlhz.doademo.mapper.MajorMapper;
import com.yjlhz.doademo.pojo.Major;
import com.yjlhz.doademo.service.MajorService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lhz
 * @title: MajorServiceImpl
 * @projectName doademo
 * @description: major业务层实现类
 * @date 2022/1/29 19:57
 */

@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    private MajorMapper majorMapper;

    @Override
    public ResultVO addMajor(MajorForm majorForm) {
        Major major = new Major();
        BeanUtils.copyProperties(majorForm,major);
        int res = majorMapper.addMajor(major);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }
}
