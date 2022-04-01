package com.yjlhz.doademo.controller;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author lhz
 * @title: testListener
 * @projectName doademo
 * @description: TODO
 * @date 2022/3/22 21:35
 */
@Slf4j
public class testListener extends AnalysisEventListener<Map<Integer, String>> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    private List<Map<Integer, String>> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

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
        List<Integer> bigQuestion = new ArrayList<>();//存储大题题号
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
                if (value != null && key > 3 && count == 1){
                    bigQuestion.add(key);
                }
                if (value != null && key > 3 && count == 3){
                    maxScores.add(Integer.valueOf(value));
                }
            }
            count++;
        }
        System.out.println(examines);
        System.out.println(maxScores);
        log.info("存储数据库成功！");
    }
}
