package com.yjlhz.doademo.controller;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.MatrixForm;
import com.yjlhz.doademo.form.ProblemListForm;
import com.yjlhz.doademo.form.ProblemObjectiveForm;
import com.yjlhz.doademo.form.WeightForm;
import com.yjlhz.doademo.pojo.Examine;
import com.yjlhz.doademo.service.ExamineService;
import com.yjlhz.doademo.service.MatrixService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lhz
 * @title: MatrixController
 * @projectName doademo
 * @description: 关系矩阵控制层
 * @date 2022/4/4 13:48
 */

@Controller
@Slf4j
@RequestMapping("/matrix")
public class MatrixController {

    @Autowired
    private MatrixService matrixService;

    @Autowired
    private ExamineService examineService;

    @PostMapping("/addMatrix")
    String addMatrix(@Param("planId")Integer planId, @Param("courseId")Integer courseId,
                     @Param("problemId")Integer[] problemId, @Param("objectiveId")Integer[] objectiveId,
                     Model model){
        List<MatrixForm> matrixForms = new ArrayList<>();
        for (int i = 0;i<problemId.length;i++){
            MatrixForm matrixForm = new MatrixForm();
            matrixForm.setProblemId(problemId[i]);
            matrixForm.setObjectiveId(objectiveId[i]);
        }
        matrixService.addMatrix(matrixForms);
        List<Examine> examineList = (List<Examine>) examineService.queryExaminesByPlanCourseId(planId, courseId).getData();
        model.addAttribute("examineList",examineList);
        return "addWeight";
    }

    @PostMapping("/addWeight")
    String addWeight(@Param("examineId")Integer[] examineId, @Param("weight")Double[] weight){
        List<WeightForm> weightForms = new ArrayList<>();

        matrixService.addWeight(weightForms);
        return "redirect:/examine/queryExamineList";
    }

}
