package com.yjlhz.doademo.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SERVER_ERROR(1,"服务器错误!"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

}
