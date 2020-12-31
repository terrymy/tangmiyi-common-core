package com.tangmiyi.future.core.bean;

import com.tangmiyi.future.core.consts.CommonReturnConstants;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


@AllArgsConstructor
@Data
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String msg;

    private Long date;

    private T data;


    public ResultBean(T data) {
        this.code = CommonReturnConstants.SUCCESS;
        this.msg = "";
        this.date = System.currentTimeMillis();
        this.data = data;
    }

    public static ResultBean success() {
        return new ResultBean(CommonReturnConstants.SUCCESS, "成功",
                System.currentTimeMillis(), null);
    }

    public static ResultBean error(String errorCode) {
        return new ResultBean(errorCode, "",
                System.currentTimeMillis(), null);
    }

    public static ResultBean error(String errorCode,String errorMsg) {
        return new ResultBean(errorCode, errorMsg,
                System.currentTimeMillis(), null);
    }

    public static ResultBean success(Object data) {
        return new ResultBean(CommonReturnConstants.SUCCESS, "成功",
                System.currentTimeMillis(), data);
    }

    public static ResultBean uncaughtedError() {
        return new ResultBean(CommonReturnConstants.UNCAUGHTED_ERROR, "未知异常",
                System.currentTimeMillis(), null);
    }

    /**
     * 是否含有错误
     * @return
     */
    public Boolean hasError() {
        return !CommonReturnConstants.SUCCESS.equals(getCode());
    }
}
