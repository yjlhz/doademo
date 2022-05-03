package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.mapper.StudentProblemMapper;
import com.yjlhz.doademo.service.StudentProblemService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lhz
 * @title: StudentProblemServiceImpl
 * @projectName doademo
 * @description: StudentProblemServiceImpl
 * @date 2022/5/3 16:15
 */

@Service
public class StudentProblemServiceImpl implements StudentProblemService {

    @Autowired
    private StudentProblemMapper studentProblemMapper;

    @Override
    public ResultVO queryBysNum(String sNum) {
        return null;
    }

    @Override
    public ResultVO deleteBysNum(String sNum) {
        int res = studentProblemMapper.deleteStudentProblemBysNum(sNum);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }
}
