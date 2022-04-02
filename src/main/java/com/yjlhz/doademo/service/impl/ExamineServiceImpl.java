package com.yjlhz.doademo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.ExamineForm;
import com.yjlhz.doademo.listener.ExamineListener;
import com.yjlhz.doademo.mapper.ExamineMapper;
import com.yjlhz.doademo.mapper.ProblemMapper;
import com.yjlhz.doademo.mapper.StudentProblemMapper;
import com.yjlhz.doademo.pojo.Course;
import com.yjlhz.doademo.pojo.Examine;
import com.yjlhz.doademo.service.ExamineService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author lhz
 * @title: ExamineServiceImpl
 * @projectName doademo
 * @description: 考核实现类
 * @date 2022/3/20 0:43
 */
@Service
public class ExamineServiceImpl implements ExamineService {

    @Autowired
    private ExamineMapper examineMapper;

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private StudentProblemMapper studentProblemMapper;

    @Override
    public ResultVO uploadExamineData(MultipartFile file, Integer planId, Integer courseId) {
        try {
            EasyExcel.read(file.getInputStream(),null, new ExamineListener(planId, courseId,examineMapper,problemMapper,studentProblemMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO queryExaminesByPlanCourseId(Integer planId,Integer courseId) {
        List<Examine> examineList = examineMapper.queryExamineByPlanCourseId(planId, courseId);
        return ResultVOUtil.success(examineList);
    }

    @Override
    public ResultVO addExamine(ExamineForm examineForm) {
        Examine examine = new Examine();
        BeanUtils.copyProperties(examineForm,examine);
        int res = examineMapper.addExamine(examine);
        if (res == -1){
            ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVOUtil.success();
    }
}
