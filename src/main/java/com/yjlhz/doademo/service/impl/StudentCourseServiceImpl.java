package com.yjlhz.doademo.service.impl;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.mapper.StudentCourseMapper;
import com.yjlhz.doademo.service.StudentCourseService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lhz
 * @title: StudentCourseServiceImpl
 * @projectName doademo
 * @description: StudentCourseServiceImpl
 * @date 2022/5/3 16:11
 */

@Service
public class StudentCourseServiceImpl implements StudentCourseService {

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Override
    public ResultVO queryBysNum(String sNum) {
        return null;
    }

    @Override
    public ResultVO deleteBysNum(String sNum) {
        int res = studentCourseMapper.deleteStudentCourseBysNum(sNum);
        if (res == -1){
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }
}
