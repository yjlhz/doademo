package com.yjlhz.doademo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.CourseForm;
import com.yjlhz.doademo.form.PlanForm;
import com.yjlhz.doademo.pojo.Plan;
import com.yjlhz.doademo.pojo.Requirement;
import com.yjlhz.doademo.pojo.Student;
import com.yjlhz.doademo.service.PlanService;
import com.yjlhz.doademo.service.RequirementService;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.http.HttpRequest;
import java.util.List;

/**
 * @author lhz
 * @title: PlanController
 * @projectName doademo
 * @description: 培养计划信息控制层接口
 * @date 2022/3/19 15:59
 */

@Controller
@Slf4j
@RequestMapping("/plan")
public class PlanController {

    @Autowired
    private PlanService planService;

    @Autowired
    private RequirementService requirementService;

    @GetMapping("/queryPlanList")
    String queryPlanList(Model model,
                         @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                         @RequestParam(defaultValue="10",value="pageSize")Integer pageSize){
        //为了程序的严谨性，判断非空：
        //设置默认当前页
        if(pageNum==null || pageNum<=0){
            pageNum = 1;
        }
        //设置默认每页显示的数据数
        if(pageSize == null){
            pageSize = 1;
        }
        System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum,pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Plan> planList = (List<Plan>) planService.queryPlans().getData();
            System.out.println("分页数据："+planList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Plan> pageInfo = new PageInfo<Plan>(planList,pageSize);
            //4.使用model传参数回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("planList",planList);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }
        return "planList";
    }

    @PostMapping("/addPlan")
    String addPlan(PlanForm planForm,Model model){
        ResultVO resultVO = planService.addPlan(planForm);
        if (resultVO.getCode() == 2){
            model.addAttribute("msg",resultVO.getMsg());
            return "IDAlreadyExists";
        }
        List<Requirement> requirementList = (List<Requirement>) requirementService.queryRequirements().getData();
        model.addAttribute("requirementList",requirementList);
        model.addAttribute("planId",planForm.getId());
        model.addAttribute("planName",planForm.getName());
        return "bindingRequirement";
    }

    @PostMapping("/bindingRequirement")
    String bindingRequirement(){

        return "bindingCourse";
    }

    @GetMapping("/toAdd")
    public String toAdd(){
        return "addPlan";
    }

    @PostMapping("/binding/{planId}")
    public String binding(@PathVariable("planId")Integer planId,HttpServletRequest request){
        String[] checkItems = request.getParameterValues("checkItem");
        System.out.println(checkItems);
        System.out.println(planId);
        return "bindingRequirement";
    }

}
