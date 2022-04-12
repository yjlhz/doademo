package com.yjlhz.doademo.utils;

import com.yjlhz.doademo.mapper.CourseMapper;
import com.yjlhz.doademo.mapper.ExamineMapper;
import com.yjlhz.doademo.mapper.PlanMapper;
import com.yjlhz.doademo.mapper.ProblemMapper;
import com.yjlhz.doademo.pojo.Examine;
import com.yjlhz.doademo.pojo.Problem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lhz
 * @title: ExportWordUtil
 * @projectName doademo
 * @description: 导出Word工具类
 * @date 2022/4/9 22:53
 */

@Slf4j
public class ExportWordUtil {

    @Autowired
    private PlanMapper planMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private ExamineMapper examineMapper;

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
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowOne.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, "课程名称");
                                new PoiWordTools().setWordCellSelfStyle(tableOneRowOne.createCell(), "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", 30, map.get("courseName"));
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


                                List<Examine> examineList = examineMapper.queryExamineByPlanCourseId(planId, courseId);
                                for (Examine examine : examineList){
                                    List<Problem> problemList = problemMapper.queryProblemByExamineId(examine.getId());
                                }
                                List<Map<String, Object>> list = new ArrayList<>();
                                if (!CollectionUtils.isEmpty(list)) {
                                    for (int i = 0; i < list.size(); i++) {
                                        Map<String, Object> Vmap = list.get(i);
                                        //表格第五行开始
                                        XWPFTableRow tableOneRowFive = tableOne.createRow();//行
                                        new PoiWordTools().setWordCellSelfStyle(tableOneRowTwo.getCell(0), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, String.valueOf(i + 1));
                                        new PoiWordTools().setWordCellSelfStyle(tableOneRowTwo.getCell(1), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, String.valueOf(Vmap.get("company_name")));
                                        new PoiWordTools().setWordCellSelfStyle(tableOneRowTwo.getCell(2), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, String.valueOf(Vmap.get("street")));
                                        new PoiWordTools().setWordCellSelfStyle(tableOneRowTwo.getCell(3), "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", 30, String.valueOf(Vmap.get("industry")));
                                    }
                                }
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
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("导出文本表格异常:" + e);
        }
    }
}
