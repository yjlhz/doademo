package com.yjlhz.doademo.utils;

import com.yjlhz.doademo.mapper.*;
import com.yjlhz.doademo.pojo.*;
import com.yjlhz.doademo.utils.ArithUtil;
import com.yjlhz.doademo.utils.PoiWordTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author lhz
 * @title: ExportWordUtil
 * @projectName doademo
 * @description: 导出Word工具类
 * @date 2022/4/9 22:53
 */

@Slf4j
@Component
public class ExportWordUtil {

    public static ExportWordUtil exportWordUtil;

    @PostConstruct
    public void init(){
        exportWordUtil=this;
    }

    @Autowired
    private PlanMapper planMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private ExamineMapper examineMapper;

    @Autowired
    private ProblemObjectiveMapper problemObjectiveMapper;

    @Autowired
    private ObjectiveMapper objectiveMapper;

    @Autowired
    private RequirementMapper requirementMapper;

    @Autowired
    private StudentProblemMapper studentProblemMapper;

    @Autowired
    private StudentMapper studentMapper;

    // word跨列合并单元格
    public  void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if ( cellIndex == fromCell ) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
    // word跨行并单元格
    public void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if ( rowIndex == fromRow ) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    public void doParagraphs(XWPFDocument doc,Map<String,String> map,Integer planId,Integer courseId) {
        try {
            List<XWPFParagraph> paragraphList = doc.getParagraphs();
            if (!CollectionUtils.isEmpty(paragraphList)) {
                for (XWPFParagraph paragraph : paragraphList) {
                    List<XWPFRun> runs = paragraph.getRuns();
                    for (XWPFRun run : runs) {
                        String text = run.getText(0);
                        if (text != null) {
                            if (text.contains("courseName")){
                                // 替换文本信息
                                String tempText = text;
                                String key = tempText.replaceAll("\\{\\{", "").replaceAll("}}", "");
                                if (!StringUtils.isEmpty(map.get(key))) {
                                    run.setText(map.get(key), 0);
                                }
                            }
                            if(text.contains("table1")){
                                //表格生成
                                run.setText("", 0);
                                XmlCursor cursor = paragraph.getCTP().newCursor();
                                XWPFTable tableOne = doc.insertNewTbl(cursor);// ---这个是关键
                                // 设置表格宽度，第一行宽度就可以了，这个值的单位，目前我也还不清楚，还没来得及研究
                                tableOne.setWidth(8000);
                                // 表格第一行，对于每个列，必须使用createCell()，而不是getCell()，因为第一行嘛，肯定是属于创建的，没有create哪里来的get呢
                                XWPFTableRow tableOneRowOne = tableOne.getRow(0);//行
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowOne.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, "课程名称");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, map.get("courseName"));
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "课程代码");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, map.get("courseId"));
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");


                                //第二行
                                XWPFTableRow tableOneRowTwo = tableOne.createRow();//行
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowTwo.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "学生专业");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowTwo.getCell(1), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, map.get("major"));
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowTwo.getCell(2), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "学生学院");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowTwo.getCell(3), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "化学与化工");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowTwo.getCell(4), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowTwo.getCell(5), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowTwo.getCell(6), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowTwo.getCell(7), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowTwo.getCell(8), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");


                                //第三行
                                XWPFTableRow tableOneRowThree = tableOne.createRow();//行
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowThree.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "毕业要求指标点（观测点）");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowThree.getCell(1), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "课程教学目标");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowThree.getCell(2), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "达成评价途径");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowThree.getCell(3), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowThree.getCell(4), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowThree.getCell(5), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowThree.getCell(6), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "评测结果");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowThree.getCell(7), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowThree.getCell(8), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "达成度");


                                //第四行
                                XWPFTableRow tableOneRowFour = tableOne.createRow();//行
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFour.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFour.getCell(1), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFour.getCell(2), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "评测方式");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFour.getCell(3), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "打分点");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFour.getCell(4), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "权重值");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFour.getCell(5), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "分值");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFour.getCell(6), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "得分");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFour.getCell(7), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "得分率");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFour.getCell(8), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "");


                                List<Examine> examineList = exportWordUtil.examineMapper.queryExamineByPlanCourseId(planId, courseId);
                                Map<Integer, Set<Problem>> classifyMap = new HashMap<>();
                                for (Examine examine : examineList){
                                    List<Problem> problemList = exportWordUtil.problemMapper.queryProblemByExamineId(examine.getId());
                                    for (Problem problem : problemList){
                                        List<ProblemObjective> problemObjectives = exportWordUtil.problemObjectiveMapper.queryByProblemId(problem.getId());
                                        for (ProblemObjective problemObjective : problemObjectives){
                                            Integer objectiveId = problemObjective.getObjectiveId();
                                            Set<Problem> orDefault = classifyMap.getOrDefault(objectiveId, new HashSet<>());
                                            orDefault.add(problem);
                                            classifyMap.put(objectiveId,orDefault);
                                        }
                                    }
                                }
                                int countRow = 3;//标记合并单元格起始位置
                                Map<Integer,Integer> mergeMap = new HashMap<>();
                                if (!CollectionUtils.isEmpty(examineList)) {
                                    int start = 4,end = 4;//记录合并单元格位置
                                    for (Map.Entry entry : classifyMap.entrySet()){
                                        Integer oId = (Integer) entry.getKey();
                                        Set<Problem> problems = (Set<Problem>) entry.getValue();
                                        double sum = 0;
                                        for (Problem pr : problems){
                                            sum = ArithUtil.add(pr.getMaxScore(),sum);
                                        }
                                        int flag = 0;//控制每个目标下的题目输出
                                        for (Problem problem : problems){
                                            XWPFTableRow tableOneRowFive = tableOne.createRow();//行
                                            if (flag == 0){
                                                Objective temp =  exportWordUtil.objectiveMapper.queryById(oId);
                                                DecimalFormat df = new DecimalFormat("#0.000");
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, exportWordUtil.requirementMapper.queryRequirementByNo(temp.getRequirementNo()).getDescription());
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(1), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, exportWordUtil.objectiveMapper.queryById(oId).getDescription());
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(2), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, exportWordUtil.examineMapper.queryExamineById(problem.getExamineId()).getDescription());
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(3), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, String.valueOf(problem.getProblemNo()));
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(4), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, df.format(ArithUtil.div(problem.getMaxScore(),sum)));
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(5), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, String.valueOf(problem.getMaxScore()));
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(6), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, df.format(ArithUtil.mul(problem.getMaxScore(),problem.getAchieve())));
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(7), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, df.format(problem.getAchieve()));
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(8), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, df.format(exportWordUtil.objectiveMapper.queryById(oId).getAchieve()));
                                                flag = 1;
                                                countRow++;
                                                end++;
                                            }else {
                                                Objective temp =  exportWordUtil.objectiveMapper.queryById(oId);
                                                DecimalFormat df = new DecimalFormat("#0.000");
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(1), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(2), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, exportWordUtil.examineMapper.queryExamineById(problem.getExamineId()).getDescription());
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(3), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, String.valueOf(problem.getProblemNo()));
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(4), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, df.format(ArithUtil.div(problem.getMaxScore(),sum)));
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(5), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, String.valueOf(problem.getMaxScore()));
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(6), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, df.format(ArithUtil.mul(problem.getMaxScore(),problem.getAchieve())));
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(7), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, df.format(problem.getAchieve()));
                                                new PoiWordTools().setWordCellSelfStyle(tableOneRowFive.getCell(8), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");
                                                countRow++;
                                                end++;
                                            }
                                        }
                                        mergeMap.put(start,end);
                                        start = end;
                                    }

                                }
                                //平均达成度
                                XWPFTableRow tableOneRowSix = tableOne.createRow();//行
                                List<Objective> objectives = exportWordUtil.objectiveMapper.queryByPlanIdAndCourseId(planId, courseId);
                                double ach = 0;
                                int c = 0;
                                for (Objective objective : objectives){
                                    ach = ArithUtil.add(objective.getAchieve(),ach);
                                    c++;
                                    ach = ArithUtil.div(ach,c);
                                }
                                DecimalFormat df = new DecimalFormat("#0.000");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSix.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "课程目标平均达成度");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSix.getCell(1), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, df.format(ach));
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSix.getCell(2), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSix.getCell(3), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSix.getCell(4), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSix.getCell(5), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSix.getCell(6), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSix.getCell(7), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSix.getCell(8), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");

                                //分析
                                XWPFTableRow tableOneRowSeven = tableOne.createRow();//行
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSeven.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "课程目标达成度分析");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSeven.getCell(1), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "这里是分析文案");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSeven.getCell(2), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSeven.getCell(3), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSeven.getCell(4), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSeven.getCell(5), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSeven.getCell(6), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSeven.getCell(7), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowSeven.getCell(8), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, "");

//                                CTVMerge vmergeStart = CTVMerge.Factory.newInstance();
//                                vmergeStart.setVal(STMerge.RESTART);
//                                CTVMerge vmergeEnd = CTVMerge.Factory.newInstance();
//                                vmergeEnd.setVal(STMerge.CONTINUE);
                                mergeCellsVertically(tableOne,8,2,3);
                                mergeCellsVertically(tableOne,0,2,3);
                                mergeCellsVertically(tableOne,1,2,3);

                                mergeCellsHorizontal(tableOne,0,3,8);
                                mergeCellsHorizontal(tableOne,1,3,8);
                                mergeCellsHorizontal(tableOne,2,2,5);
                                mergeCellsHorizontal(tableOne,2,6,7);
                                mergeCellsHorizontal(tableOne,countRow+1,1,8);
                                mergeCellsHorizontal(tableOne,countRow+2,1,8);

                                for (Map.Entry entry : mergeMap.entrySet()){
                                    int key = (int) entry.getKey();
                                    int value = (int) entry.getValue();
                                    mergeCellsVertically(tableOne,0,key,value-1);
                                    mergeCellsVertically(tableOne,1,key,value-1);
                                    mergeCellsVertically(tableOne,8,key,value);
                                }

                            }

                            //输出成绩单
                            if (text.contains("table2")){
                                //获取该专业课程所有问题
                                List<Examine> examineList = exportWordUtil.examineMapper.queryExamineByPlanCourseId(planId, courseId);
                                List<Problem> problemList = new ArrayList<>();
                                for (Examine examine : examineList){
                                    List<Problem> temp = exportWordUtil.problemMapper.queryProblemByExamineId(examine.getId());
                                    for (Problem problem : temp){
                                        problemList.add(problem);
                                    }
                                }
                                //获取该教学计划所有学生
                                Plan plan = exportWordUtil.planMapper.queryPlanById(planId);
                                List<Student> studentList = exportWordUtil.studentMapper.queryStudentByPlanId(plan.getMajor(), plan.getGrade());
                                //题目超过8题，分表
                                //表格生成
                                run.setText("", 0);
                                XmlCursor cursor = paragraph.getCTP().newCursor();
                                XWPFTable tableTwo = doc.insertNewTbl(cursor);// ---这个是关键
                                // 设置表格宽度，第一行宽度就可以了，这个值的单位，目前我也还不清楚，还没来得及研究
                                tableTwo.setWidth(8000);
                                if (problemList.size()>8){
                                    List<Problem> temp = new ArrayList<>();
                                    for (int i = 0;i<problemList.size();i++){
                                        Problem p = problemList.get(i);
                                        temp.add(p);
                                        if (i%7==0 && i!=0){
                                            // 表格第一行，对于每个列，必须使用createCell()，而不是getCell()，因为第一行嘛，肯定是属于创建的，没有create哪里来的get呢
                                            XWPFTableRow tableTwoRowOne = tableTwo.getRow(0);//行
                                            new PoiWordTools().setWordCellSelfStyle(tableTwoRowOne.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, "班级");
                                            new PoiWordTools().setWordCellSelfStyle(tableTwoRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, "学号");
                                            new PoiWordTools().setWordCellSelfStyle(tableTwoRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, "姓名");
                                            new PoiWordTools().setWordCellSelfStyle(tableTwoRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, temp.get(0).getExamineId()+"-"+temp.get(0).getProblemNo());
                                            new PoiWordTools().setWordCellSelfStyle(tableTwoRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, temp.get(1).getExamineId()+"-"+temp.get(1).getProblemNo());
                                            new PoiWordTools().setWordCellSelfStyle(tableTwoRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, temp.get(2).getExamineId()+"-"+temp.get(2).getProblemNo());
                                            new PoiWordTools().setWordCellSelfStyle(tableTwoRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, temp.get(3).getExamineId()+"-"+temp.get(3).getProblemNo());
                                            new PoiWordTools().setWordCellSelfStyle(tableTwoRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, temp.get(4).getExamineId()+"-"+temp.get(4).getProblemNo());
                                            new PoiWordTools().setWordCellSelfStyle(tableTwoRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, temp.get(5).getExamineId()+"-"+temp.get(5).getProblemNo());
                                            new PoiWordTools().setWordCellSelfStyle(tableTwoRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, temp.get(6).getExamineId()+"-"+temp.get(6).getProblemNo());
                                            new PoiWordTools().setWordCellSelfStyle(tableTwoRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, temp.get(7).getExamineId()+"-"+temp.get(7).getProblemNo());
                                            for (Student student : studentList){
                                                XWPFTableRow tableTwoRowThree = tableTwo.createRow();//行
                                                new PoiWordTools().setWordCellSelfStyle(tableTwoRowThree.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 40, student.getClasses());
                                                new PoiWordTools().setWordCellSelfStyle(tableTwoRowThree.getCell(1), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 40, student.getSNum());
                                                new PoiWordTools().setWordCellSelfStyle(tableTwoRowThree.getCell(2), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 40, student.getSName());
                                                int index = 3;
                                                for (Problem problem : temp){
                                                    StudentProblem studentProblem = exportWordUtil.studentProblemMapper.queryStudentProblemsBySNumAndProblemId(student.getSNum(), problem.getId());
                                                    new PoiWordTools().setWordCellSelfStyle(tableTwoRowThree.getCell(index), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 40, String.valueOf(studentProblem.getScore()));
                                                    index++;
                                                }
                                            }
                                            temp.clear();
                                        }
                                    }
                                    if (problemList.size()%8 != 0){
                                        int surIndex = problemList.size() / 8;
                                        surIndex *= 8;
                                        int size = temp.size();
                                        //表格生成
                                        // 设置表格宽度，第一行宽度就可以了，这个值的单位，目前我也还不清楚，还没来得及研究
                                        tableTwo.setWidth(8000);
                                        // 表格第一行，对于每个列，必须使用createCell()，而不是getCell()，因为第一行嘛，肯定是属于创建的，没有create哪里来的get呢
                                        XWPFTableRow tableTwoRowFour = tableTwo.createRow();//行
                                        new PoiWordTools().setWordCellSelfStyle(tableTwoRowFour.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, "班级");
                                        new PoiWordTools().setWordCellSelfStyle(tableTwoRowFour.getCell(1), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, "学号");
                                        new PoiWordTools().setWordCellSelfStyle(tableTwoRowFour.getCell(2), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, "姓名");
                                        for (int i = 0;i<size;i++){
                                            new PoiWordTools().setWordCellSelfStyle(tableTwoRowFour.getCell(i+3), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, problemList.get(surIndex).getExamineId()+"-"+problemList.get(surIndex).getProblemNo());
                                            surIndex++;
                                        }
                                        for (Student student : studentList){
                                            XWPFTableRow tableTwoRowFive = tableTwo.createRow();//行
                                            new PoiWordTools().setWordCellSelfStyle(tableTwoRowFive.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 40, student.getClasses());
                                            new PoiWordTools().setWordCellSelfStyle(tableTwoRowFive.getCell(1), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 40, student.getSNum());
                                            new PoiWordTools().setWordCellSelfStyle(tableTwoRowFive.getCell(2), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 40, student.getSName());
                                            int index = 3;
                                            for (Problem problem : temp){
                                                StudentProblem studentProblem = exportWordUtil.studentProblemMapper.queryStudentProblemsBySNumAndProblemId(student.getSNum(), problem.getId());
                                                new PoiWordTools().setWordCellSelfStyle(tableTwoRowFive.getCell(index), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 40, String.valueOf(studentProblem.getScore()));
                                                index++;
                                            }
                                        }
                                    }
                                }else {
                                    int size = problemList.size();
                                    // 设置表格宽度，第一行宽度就可以了，这个值的单位，目前我也还不清楚，还没来得及研究
                                    tableTwo.setWidth(8000);
                                    // 表格第一行，对于每个列，必须使用createCell()，而不是getCell()，因为第一行嘛，肯定是属于创建的，没有create哪里来的get呢
                                    XWPFTableRow tableTwoRowOne = tableTwo.getRow(0);//行
                                    new PoiWordTools().setWordCellSelfStyle(tableTwoRowOne.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, "班级");
                                    new PoiWordTools().setWordCellSelfStyle(tableTwoRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, "学号");
                                    new PoiWordTools().setWordCellSelfStyle(tableTwoRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, "姓名");
                                    for (int i = 0;i<size;i++){
                                        new PoiWordTools().setWordCellSelfStyle(tableTwoRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 40, problemList.get(i).getExamineId()+"-"+problemList.get(i).getProblemNo());
                                    }
                                    for (Student student : studentList){
                                        XWPFTableRow tableTwoRowThree = tableTwo.createRow();//行
                                        new PoiWordTools().setWordCellSelfStyle(tableTwoRowThree.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 40, student.getClasses());
                                        new PoiWordTools().setWordCellSelfStyle(tableTwoRowThree.getCell(1), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 40, student.getSNum());
                                        new PoiWordTools().setWordCellSelfStyle(tableTwoRowThree.getCell(2), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 40, student.getSName());
                                        int index = 3;
                                        for (Problem problem : problemList){
                                            StudentProblem studentProblem = exportWordUtil.studentProblemMapper.queryStudentProblemsBySNumAndProblemId(student.getSNum(), problem.getId());
                                            new PoiWordTools().setWordCellSelfStyle(tableTwoRowThree.getCell(index), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 40, String.valueOf(studentProblem.getScore()));
                                            index++;
                                        }
                                    }
                                }

                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("导出文本表格异常:" + e);
        }
    }


    /**
     * 处理图表
     *
     * @param doc
     * @throws FileNotFoundException
     */
    public void doCharts(XWPFDocument doc,Map<String,String> map) {
        /**----------------------------处理图表------------------------------------**/

/**----------------------------处理图表------------------------------------**/

        // 数据准备
        List<String> titleArr = new ArrayList<String>();// 标题
        titleArr.add("title");
        titleArr.add("金额");

        List<String> fldNameArr = new ArrayList<String>();// 字段名
        fldNameArr.add("item1");
        fldNameArr.add("item2");

        // 数据集合
        List<Map<String, String>> listItemsByType = new ArrayList<Map<String, String>>();

        // 第一行数据
        Map<String, String> base1 = new HashMap<String, String>();
        base1.put("item1", "材料费用");
        base1.put("item2", "500");
        base1.put("item3", "600");

        // 第二行数据
        Map<String, String> base2 = new HashMap<String, String>();
        base2.put("item1", "出差费用");
        base2.put("item2", "300");
        base2.put("item3", "500");

        // 第三行数据
        Map<String, String> base3 = new HashMap<String, String>();
        base3.put("item1", "住宿费用");
        base3.put("item2", "300");
        base3.put("item3", "500");

        listItemsByType.add(base1);
        listItemsByType.add(base2);
        listItemsByType.add(base3);


        // 获取word模板中的所有图表元素，用map存放
        // 为什么不用list保存：查看doc.getRelations()的源码可知，源码中使用了hashMap读取文档图表元素，
        // 对relations变量进行打印后发现，图表顺序和文档中的顺序不一致，也就是说relations的图表顺序不是文档中从上到下的顺序
        Map<String, POIXMLDocumentPart> chartsMap = new HashMap<String, POIXMLDocumentPart>();
        //动态刷新图表
        List<POIXMLDocumentPart> relations = doc.getRelations();
        for (POIXMLDocumentPart poixmlDocumentPart : relations) {
            if (poixmlDocumentPart instanceof XWPFChart) {  // 如果是图表元素
                String str = poixmlDocumentPart.toString();
                System.out.println("str：" + str);
                String key = str.replaceAll("Name: ", "")
                        .replaceAll(" - Content Type: application/vnd\\.openxmlformats-officedocument\\.drawingml\\.chart\\+xml", "").trim();
                System.out.println("key：" + key);

                chartsMap.put(key, poixmlDocumentPart);
            }
        }

        System.out.println("\n图表数量：" + chartsMap.size() + "\n");


//        // 第一个图表-条形图
//        POIXMLDocumentPart poixmlDocumentPart0 = chartsMap.get("/word/charts/chart1.xml");
//        new PoiWordTools().replaceBarCharts(poixmlDocumentPart0, titleArr, fldNameArr, listItemsByType);
//
//        // 第二个-柱状图
//        POIXMLDocumentPart poixmlDocumentPart1 = chartsMap.get("/word/charts/chart2.xml");
//        new PoiWordTools().replaceBarCharts(poixmlDocumentPart1, titleArr, fldNameArr, listItemsByType);

        // 第三个图表-多列柱状图
        doCharts3(chartsMap);
//
//        // 第四个图表-折线图
//        doCharts4(chartsMap);
//
//        // 第五个图表-饼图
//        POIXMLDocumentPart poixmlDocumentPart4 = chartsMap.get("/word/charts/chart5.xml");
//        new PoiWordTools().replacePieCharts(poixmlDocumentPart4, titleArr, fldNameArr, listItemsByType);

//
//        doCharts6(chartsMap);
    }

    public void doCharts3(Map<String, POIXMLDocumentPart> chartsMap) {
        // 数据准备
        List<String> titleArr = new ArrayList<String>();// 标题
        titleArr.add("姓名");
        titleArr.add("欠款");
        titleArr.add("存款");

        List<String> fldNameArr = new ArrayList<String>();// 字段名
        fldNameArr.add("item1");
        fldNameArr.add("item2");
        fldNameArr.add("item3");

        // 数据集合
        List<Map<String, String>> listItemsByType = new ArrayList<Map<String, String>>();

        // 第一行数据
        Map<String, String> base1 = new HashMap<String, String>();
        base1.put("item1", "老张");
        base1.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base1.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");

        // 第二行数据
        Map<String, String> base2 = new HashMap<String, String>();
        base2.put("item1", "老李");
        base2.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base2.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");

        // 第三行数据
        Map<String, String> base3 = new HashMap<String, String>();
        base3.put("item1", "老刘");
        base3.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base3.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");


        listItemsByType.add(base1);
        listItemsByType.add(base2);
        listItemsByType.add(base3);

        POIXMLDocumentPart poixmlDocumentPart2 = chartsMap.get("/word/charts/chart1.xml");
        new PoiWordTools().replaceBarCharts(poixmlDocumentPart2, titleArr, fldNameArr, listItemsByType);
    }



    public void doCharts4(Map<String, POIXMLDocumentPart> chartsMap) {
        // 数据准备
        List<String> titleArr = new ArrayList<String>();// 标题
        titleArr.add("title");
        titleArr.add("占基金资产净值比例22222（%）");
        titleArr.add("额外的（%）");
        titleArr.add("额外的（%）");

        List<String> fldNameArr = new ArrayList<String>();// 字段名
        fldNameArr.add("item1");
        fldNameArr.add("item2");
        fldNameArr.add("item3");
        fldNameArr.add("item4");

        // 数据集合
        List<Map<String, String>> listItemsByType = new ArrayList<Map<String, String>>();

        // 第一行数据
        Map<String, String> base1 = new HashMap<String, String>();
        base1.put("item1", "材料费用");
        base1.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base1.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base1.put("item4", (int)(1 + Math.random() * (100 - 1 + 1)) + "");

        // 第二行数据
        Map<String, String> base2 = new HashMap<String, String>();
        base2.put("item1", "出差费用");
        base2.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base2.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base2.put("item4", (int)(1 + Math.random() * (100 - 1 + 1)) + "");

        // 第三行数据
        Map<String, String> base3 = new HashMap<String, String>();
        base3.put("item1", "住宿费用");
        base3.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base3.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base3.put("item4", (int)(1 + Math.random() * (100 - 1 + 1)) + "");


        listItemsByType.add(base1);
        listItemsByType.add(base2);
        listItemsByType.add(base3);

        POIXMLDocumentPart poixmlDocumentPart2 = chartsMap.get("/word/charts/chart4.xml");
        new PoiWordTools().replaceLineCharts(poixmlDocumentPart2, titleArr, fldNameArr, listItemsByType);
    }


    /**
     * 对应文档中的第6个图表（预处理—分公司情况）
     */
    public void doCharts6(Map<String, POIXMLDocumentPart> chartsMap) {
        // 数据准备
        List<String> titleArr = new ArrayList<String>();// 标题
        titleArr.add("title");
        titleArr.add("投诉受理量（次）");
        titleArr.add("预处理拦截工单量（次）");
        titleArr.add("拦截率");

        List<String> fldNameArr = new ArrayList<String>();// 字段名
        fldNameArr.add("item1");
        fldNameArr.add("item2");
        fldNameArr.add("item3");
        fldNameArr.add("item4");

        // 数据集合
        List<Map<String, String>> listItemsByType = new ArrayList<Map<String, String>>();

        // 第一行数据
        Map<String, String> base1 = new HashMap<String, String>();
        base1.put("item1", "通辽");
        base1.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base1.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base1.put("item4", (int)(1 + Math.random() * (100 - 1 + 1)) + "");

        // 第二行数据
        Map<String, String> base2 = new HashMap<String, String>();
        base2.put("item1", "呼和浩特");
        base2.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base2.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base2.put("item4", (int)(1 + Math.random() * (100 - 1 + 1)) + "");

        // 第三行数据
        Map<String, String> base3 = new HashMap<String, String>();
        base3.put("item1", "锡林郭勒");
        base3.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base3.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base3.put("item4", (int)(1 + Math.random() * (100 - 1 + 1)) + "");

        // 第四行数据
        Map<String, String> base4 = new HashMap<String, String>();
        base4.put("item1", "阿拉善");
        base4.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base4.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base4.put("item4", (int)(1 + Math.random() * (100 - 1 + 1)) + "");

        // 第五行数据
        Map<String, String> base5 = new HashMap<String, String>();
        base5.put("item1", "巴彦淖尔");
        base5.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base5.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base5.put("item4", (int)(1 + Math.random() * (100 - 1 + 1)) + "");

        // 第六行数据
        Map<String, String> base6 = new HashMap<String, String>();
        base6.put("item1", "兴安");
        base6.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base6.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base6.put("item4", (int)(1 + Math.random() * (100 - 1 + 1)) + "");

        // 第七行数据
        Map<String, String> base7 = new HashMap<String, String>();
        base7.put("item1", "乌兰察布");
        base7.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base7.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base7.put("item4", (int)(1 + Math.random() * (100 - 1 + 1)) + "");

        // 第八行数据
        Map<String, String> base8 = new HashMap<String, String>();
        base8.put("item1", "乌海");
        base8.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base8.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base8.put("item4", (int)(1 + Math.random() * (100 - 1 + 1)) + "");

        // 第九行数据
        Map<String, String> base9 = new HashMap<String, String>();
        base9.put("item1", "赤峰");
        base9.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base9.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base9.put("item4", (int)(1 + Math.random() * (100 - 1 + 1)) + "");

        // 第十行数据
        Map<String, String> base10 = new HashMap<String, String>();
        base10.put("item1", "包头");
        base10.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base10.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base10.put("item4", (int)(1 + Math.random() * (100 - 1 + 1)) + "");

        // 第十一行数据
        Map<String, String> base11 = new HashMap<String, String>();
        base11.put("item1", "呼伦贝尔");
        base11.put("item2", (int)(int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base11.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base11.put("item4", (int)(1 + Math.random() * (100 - 1 + 1)) + "");

        // 第十二行数据
        Map<String, String> base12 = new HashMap<String, String>();
        base12.put("item1", "鄂尔多斯");
        base12.put("item2", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base12.put("item3", (int)(1 + Math.random() * (100 - 1 + 1)) + "");
        base12.put("item4", (int)(1 + Math.random() * (100 - 1 + 1)) + "");

        listItemsByType.add(base1);
        listItemsByType.add(base2);
        listItemsByType.add(base3);
        listItemsByType.add(base4);
        listItemsByType.add(base5);
        listItemsByType.add(base6);
        listItemsByType.add(base7);
        listItemsByType.add(base8);
        listItemsByType.add(base9);
        listItemsByType.add(base10);
        listItemsByType.add(base11);
        listItemsByType.add(base12);

        // 下标0的图表-折线图
        POIXMLDocumentPart poixmlDocumentPart5 = chartsMap.get("/word/charts/chart6.xml");
        new PoiWordTools().replaceCombinationCharts(poixmlDocumentPart5, titleArr, fldNameArr, listItemsByType);
    }

}
