package com.yjlhz.doademo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.yjlhz.doademo.dto.FirstRequirementDTO;
import com.yjlhz.doademo.dto.SecondRequirementDTO;
import com.yjlhz.doademo.mapper.FirstRequirementMapper;
import com.yjlhz.doademo.mapper.SecondRequirementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author lhz
 * @title: SecondRequirementListener
 * @projectName doademo
 * @description: 二级指标点上传监听类
 * @date 2022/3/27 15:21
 */
public class SecondRequirementListener extends AnalysisEventListener<SecondRequirementDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseListener.class);

    private static final int BATCH_COUNT = 100;
    List<SecondRequirementDTO> list = new ArrayList<>();

    private final SecondRequirementMapper secondRequirementMapper;

    public SecondRequirementListener(SecondRequirementMapper secondRequirementMapper) {
        this.secondRequirementMapper = secondRequirementMapper;
    }

    @Override
    public void invoke(SecondRequirementDTO data, AnalysisContext context) {

        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        Iterator<SecondRequirementDTO> iterator = list.iterator();
        while (iterator.hasNext()) {
            secondRequirementMapper.uploadSecondRequirement(iterator.next());
        }
        LOGGER.info("存储数据库成功！");
    }
}
