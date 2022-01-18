package com.yjlhz.doademo.utils;

import com.yjlhz.doademo.enums.ResultEnum;
import com.yjlhz.doademo.vo.ResultVO;
import lombok.Data;

@Data
public class ResultVOUtil {
    /**
     * @param object
     * @return 有参成功结果
     * @author yjlhz
     */
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    /***
     *
     * 无参成功结果
     *
     * @author yjlhz
     */
    public static ResultVO success() {
        return success(null);
    }

    /**
     * 返回错误结果
     *
     * @param resultEnum
     */
    public static ResultVO error(ResultEnum resultEnum) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMsg(resultEnum.getMsg());
        return resultVO;
    }

    public static ResultVO error(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(400);
        resultVO.setMsg("错误");
        resultVO.setData(object);
        return resultVO;
    }
}
