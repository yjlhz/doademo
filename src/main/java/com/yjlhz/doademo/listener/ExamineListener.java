package com.yjlhz.doademo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.yjlhz.doademo.mapper.ExamineMapper;
import com.yjlhz.doademo.mapper.ProblemMapper;
import com.yjlhz.doademo.mapper.StudentProblemMapper;
import com.yjlhz.doademo.pojo.Examine;
import com.yjlhz.doademo.pojo.Problem;
import com.yjlhz.doademo.pojo.StudentProblem;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author lhz
 * @title: testListener
 * @projectName doademo
 * @description: ExamineListener
 * @date 2022/3/22 21:35
 */
@Slf4j
public class ExamineListener extends AnalysisEventListener<Map<Integer, String>> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    private List<Map<Integer, String>> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private Integer planId;
    private Integer courseId;
    private ExamineMapper examineMapper;
    private ProblemMapper problemMapper;
    private StudentProblemMapper studentProblemMapper;

    public ExamineListener(Integer planId,Integer courseId,ExamineMapper examineMapper,ProblemMapper problemMapper,StudentProblemMapper studentProblemMapper){
        this.planId = planId;
        this.courseId = courseId;
        this.examineMapper = examineMapper;
        this.problemMapper = problemMapper;
        this.studentProblemMapper = studentProblemMapper;
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        System.out.println("当前行:"+context.getCurrentRowNum());
        System.out.println(data);
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    @Transactional
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        Iterator<Map<Integer,String>> iterator = cachedDataList.iterator();
        int count = 0;//用于计数，前面五行为表头信息
        List<Integer> examines = new ArrayList<>();//储存考核key值
        List<Integer> maxScores = new ArrayList<>();//储存问题满分值
        while(iterator.hasNext() && count<4){
            //获取一行数据
            Map<Integer,String> myData = iterator.next();
            //解析分割数据
            for (Map.Entry entry : myData.entrySet()) {
                Integer key = (Integer) entry.getKey();//获取第几列
                String value = (String) entry.getValue();//数据
                //列数从0开始算，模板第三列为解释性文字，不需要处理
                if (value != null && key != 3 && count == 0) {
                    examines.add(key);
                }
                if (value != null && key > 3 && count == 3){
                    maxScores.add(Integer.valueOf(value));
                }
            }
            count++;
        }
        //统计题目数
        Map<Integer, String> integerStringMap = cachedDataList.get(4);
        int proSize = integerStringMap.size();
        //添加考核数据
        Map<Integer,String> examineMap = cachedDataList.get(0);
        for (Integer key : examines){
            String description = examineMap.get(key);
            Examine examine = new Examine();
            examine.setPlanId(planId);
            examine.setCourseId(courseId);
            examine.setDescription(description);
            examine.setWeight(0.0);
            examineMapper.addExamine(examine);
            System.out.println(description);
        }
        //添加问题数据
        int index = 0,no = 1,maxIndex = 0;
        int examineId = -1;
        String des = "";
        for (Map.Entry entry : examineMap.entrySet()){
            if (entry.getValue()==null && index > 3){
                Problem problem = new Problem();
                problem.setExamineId(examineId);
                problem.setProblemNo(no++);
                problem.setMaxScore(Double.valueOf(maxScores.get(maxIndex++)));
                problem.setAchieve(0.0);
                problemMapper.addProblem(problem);
            }else if (index > 3){
                String description = (String) entry.getValue();
                no=1;
                Examine examine = examineMapper.queryByPlanIdAndCourseIdAndDescription(planId,courseId,description);
                Problem problem = new Problem();
                problem.setExamineId(examine.getId());
                problem.setProblemNo(no++);
                problem.setMaxScore(Double.valueOf(maxScores.get(maxIndex++)));
                problem.setAchieve(0.0);
                examineId = examine.getId();
                problemMapper.addProblem(problem);
                des = description;
            }
            index++;
        }
        if (index != proSize-1){
            for (int i = index;i<proSize;i++){
                Examine examine = examineMapper.queryByPlanIdAndCourseIdAndDescription(planId,courseId,des);
                Problem problem = new Problem();
                problem.setExamineId(examine.getId());
                problem.setProblemNo(no++);
                problem.setMaxScore(Double.valueOf(maxScores.get(maxIndex++)));
                problem.setAchieve(0.0);
                problemMapper.addProblem(problem);
            }
        }
        //获取问题id
        List<Integer> problemIds = new ArrayList<>();
        List<Examine> examineList = examineMapper.queryExamineByPlanCourseId(planId,courseId);
        for (Examine examine : examineList){
            List<Problem> problemList = problemMapper.queryProblemByExamineId(examine.getId());
            for (Problem problem : problemList){
                problemIds.add(problem.getId());
            }
        }
        //解析学生得分数据
        int problemIdx = 0;
        while (iterator.hasNext()){
            //获取一个学生所有题目的得分情况
            Map<Integer,String> myData = iterator.next();
            for (Map.Entry entry : myData.entrySet()){
                Integer key = (Integer) entry.getKey();
                String value = (String) entry.getValue();
                if (key > 3){
                    StudentProblem studentProblem = new StudentProblem();
                    studentProblem.setSNum(myData.get(1));
                    studentProblem.setProblemId(problemIds.get(problemIdx++));
                    studentProblem.setScore(Double.valueOf(value));
                    studentProblemMapper.addStudentProblem(studentProblem);
                }
            }
            problemIdx = 0;
        }
        System.out.println("planId:"+planId);
        System.out.println("courseId:"+courseId);
        System.out.println("考核key:"+examines);
        System.out.println("满分:"+maxScores);
        log.info("存储数据库成功！");
    }
}
