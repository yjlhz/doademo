package com.yjlhz.doademo.controller;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.form.ExamineForm;
import com.yjlhz.doademo.form.QueryExamineForm;
import com.yjlhz.doademo.listener.ExamineListener;
import com.yjlhz.doademo.pojo.*;
import com.yjlhz.doademo.service.*;
import com.yjlhz.doademo.utils.ResultVOUtil;
import com.yjlhz.doademo.vo.ExamineVO;
import com.yjlhz.doademo.vo.ProblemVO;
import com.yjlhz.doademo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lhz
 * @title: ExamineController
 * @projectName doademo
 * @description: 考核控制层类
 * @date 2022/3/20 0:48
 */

@Controller
@Slf4j
@RequestMapping("/examine")
public class ExamineController {

    @Autowired
    private ExamineService examineService;

    @Autowired
    private PlanService planService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private ProblemObjectiveService problemObjectiveService;

    @Autowired
    private ProblemRequirementService problemRequirementService;

    @Autowired
    private StudentProblemService studentProblemService;

    @Autowired
    private ObjectiveService objectiveService;

    @PostMapping("/uploadExamineData")
    String uploadExamineData(@RequestParam(value = "file", required = false) MultipartFile file,Integer planId,Integer courseId,Model model){
        examineService.uploadExamineData(file,planId,courseId);
        List<Examine> examineList = (List<Examine>) examineService.queryExaminesByPlanCourseId(planId, courseId).getData();
        List<ProblemVO> problemList = new ArrayList<>();
        for (Examine examine : examineList){
            List<Problem> problems = ((List<Problem>) problemService.queryProblemsByExamineId(examine.getId()).getData());
            for (Problem problem : problems){
                ProblemVO problemVO = new ProblemVO();
                problemVO.setExamineId(examine.getId());
                problemVO.setExamineName(examine.getDescription());
                problemVO.setId(problem.getId());
                problemVO.setNo(problem.getProblemNo());
                problemList.add(problemVO);
            }
        }
        Plan plan = (Plan) planService.queryPlanById(planId).getData();
        Course course = (Course) courseService.queryCourseById(courseId).getData();
        List<Objective> objectiveList = (List<Objective>) objectiveService.queryByPlanIdAndCourseId(planId, courseId).getData();
        model.addAttribute("plan",plan);
        model.addAttribute("course",course);
        model.addAttribute("problemList",problemList);
        model.addAttribute("objectiveList",objectiveList);
        return "bindingObjective";
    }

    @GetMapping("/download")
    void download(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取model路径
            String realPath = "C:/Users/Lenovo/Desktop/doademo/src/main/resources"+ File.separator + "examineTemplate.xlsx";

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(realPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            //清空response
            response.reset();
            //设置response响应头
            response.setCharacterEncoding("UTF-8");
            response.setContentType("multipart/form-data");
            response.setContentType("application/x-download");
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Disposition","attachment;fileName="+ URLEncoder.encode(URLEncoder.encode("examineTemplate.xlsx","UTF-8"),"ISO-8859-1"));

            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/toUpload")
    public String toUpload(Model model){
        List<Plan> planList = (List<Plan>) planService.queryPlans().getData();
        List<Course> courseList = (List<Course>) courseService.queryCourses().getData();
        model.addAttribute("planList",planList);
        model.addAttribute("courseList",courseList);
        return "uploadExamine";
    }

    @GetMapping("/toUpload1")
    public String toUpload1(Model model,
                            @RequestParam(value = "planId")Integer planId,
                            @RequestParam(value = "courseId")Integer courseId){
        List<Plan> planList = (List<Plan>) planService.queryPlans().getData();
        List<Course> courseList = (List<Course>) courseService.queryCourses().getData();
        model.addAttribute("planList",planList);
        model.addAttribute("courseList",courseList);
        model.addAttribute("planId",planId);
        model.addAttribute("courseId",courseId);
        return "uploadExamine";
    }

    @GetMapping("/queryExamineById")
    ResultVO queryExamineById(Integer planId,Integer courseId){
        return examineService.queryExaminesByPlanCourseId(planId, courseId);
    }

    @PostMapping("/addExamine")
    ResultVO addExamine(@Valid ExamineForm examineForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }
        return examineService.addExamine(examineForm);
    }

    @GetMapping("/queryExamineList")
    public String queryCourseList(Model model,
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
            List<ExamineVO> examineList = (List<ExamineVO>) examineService.queryExamines().getData();
            System.out.println("分页数据："+examineList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<ExamineVO> pageInfo = new PageInfo<ExamineVO>(examineList,pageSize);
            //4.使用model传参数回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("examineList",examineList);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }
        List<Plan> planList = (List<Plan>) planService.queryPlans().getData();
        List<Course> courseList = (List<Course>) courseService.queryCourses().getData();
        model.addAttribute("planList",planList);
        model.addAttribute("courseList",courseList);
        return "examineList";
    }

    @PostMapping("/toQuery")
    public String toQuery(QueryExamineForm queryExamineForm,Model model,
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
            List<Examine> examineList1 = (List<Examine>) examineService.queryExaminesByPlanCourseId(queryExamineForm.getPlanId(),queryExamineForm.getCourseId()).getData();
            List<ExamineVO> examineList = new ArrayList<>();
            for (Examine examine : examineList1){
                ExamineVO examineVO = new ExamineVO();
                examineVO.setId(examine.getId());
                Plan plan = (Plan) planService.queryPlanById(examine.getPlanId()).getData();
                examineVO.setPlanName(plan.getName());
                Course course = (Course) courseService.queryCourseById(examine.getCourseId()).getData();
                examineVO.setCourseName(course.getCourseName());
                examineVO.setDescription(examine.getDescription());
                examineVO.setWeight(examine.getWeight());
                examineList.add(examineVO);
            }
            System.out.println("分页数据："+examineList);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<ExamineVO> pageInfo = new PageInfo<ExamineVO>(examineList,pageSize);
            //4.使用model传参数回前端
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("examineList",examineList);
        }finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }
        List<Plan> planList = (List<Plan>) planService.queryPlans().getData();
        List<Course> courseList = (List<Course>) courseService.queryCourses().getData();
        Plan thisPlan = (Plan) planService.queryPlanById(queryExamineForm.getPlanId()).getData();
        Course thisCourse = (Course) courseService.queryCourseById(queryExamineForm.getCourseId()).getData();
        model.addAttribute("planList",planList);
        model.addAttribute("courseList",courseList);
        model.addAttribute("planId",queryExamineForm.getPlanId());
        model.addAttribute("courseId",queryExamineForm.getCourseId());
        model.addAttribute("planName",thisPlan.getName());
        model.addAttribute("courseName",thisCourse.getCourseName());
        return "queryExamineByPlanIdAndCourseId";
    }

    @GetMapping("/deleteExamine/{id}")
    @Transactional
    public String deleteExamine(@PathVariable Integer id){
        examineService.deleteExamineById(id);
        List<Problem> problemList = (List<Problem>) problemService.queryProblemsByExamineId(id).getData();
        for (Problem problem : problemList){
            problemService.deleteById(problem.getId());
            problemObjectiveService.deleteByProblemId(problem.getId());
            problemRequirementService.deleteByProblemId(problem.getId());
            studentProblemService.deleteByProblemId(problem.getId());
        }
        return "redirect:/examine/queryExamineList";
    }

    @GetMapping("/toAddWeight/{id}")
    public String toAddWeight(@PathVariable Integer id,Model model){
        Examine examine = (Examine) examineService.queryExamineById(id).getData();
        List<Examine> examineList = (List<Examine>) examineService.queryExaminesByPlanCourseId(examine.getPlanId(), examine.getCourseId()).getData();
        model.addAttribute("examineList",examineList);
        return "addWeight";
    }

}
