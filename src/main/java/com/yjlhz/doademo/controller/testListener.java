package com.yjlhz.doademo.controller;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
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
        int count = 0;
        while(iterator.hasNext()){
            Map<Integer,String> myData = iterator.next();
            if (count<5){
                for (Map.Entry entry : myData.entrySet()){
                    Integer key = (Integer)entry.getKey();
                    String value = (String)entry.getValue();
                    System.out.println(key);
                    System.out.println(value);
                }
            }else {

            }
        }
        log.info("存储数据库成功！");
    }
}
