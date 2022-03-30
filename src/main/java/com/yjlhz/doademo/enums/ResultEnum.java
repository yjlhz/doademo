package com.yjlhz.doademo.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    //service层
    SERVER_ERROR(1,"服务器错误!"),
    CONFLICT_ERROR(2,"ID已存在!"),
    BINDING_ERROR(3,"关系未指定!"),
    NOTHINGNESS_ERROR(4,"学生指定培养计划不存在!"),

    //controller层
    PARAMETER_ERROR(100,"参数不合法!"),
    NULLPOINT_ERROR(101,"数据不存在!"),

    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

}
