//package com.yjlhz.doademo.mapper;
//
//import com.yjlhz.doademo.pojo.Major;
//import org.junit.Assert;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * @author lhz
// * @title: MajorMapperTest
// * @projectName doademo
// * @description: major dao层测试类
// * @date 2022/1/2716:56
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//class MajorMapperTest {
//
//    @Autowired
//    private MajorMapper majorMapper;
//
//    @Test
//    void queryMajorList() {
//        List<Major> majors = majorMapper.queryMajorList();
//        Assert.assertNotNull(majors);
//    }
//
//    @Test
//    void queryMajorByName() {
//        Major major = majorMapper.queryMajorByName("应用化学");
//        Assert.assertNotNull(major);
//    }
//
//    @Test
//    void queryMajorById() {
//        Major major = majorMapper.queryMajorById(1);
//        Assert.assertNotNull(major);
//    }
//
//    @Test
//    void addMajor() {
//        Major major = new Major();
//        major.setMajorName("能源化学工程");
//        int res = majorMapper.addMajor(major);
//        Assert.assertNotEquals(res,-1);
//    }
//}