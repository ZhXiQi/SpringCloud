package com.springcloud.service.common;

import com.springcloud.service.constant.Code;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/5/27 15:53
 */
public class BaseResult<T> {

    private int code;

    private String msg;

    private T data;

    public BaseResult() {
        //空对象json串
        this.data = (T) new Object();
    }

    public BaseResult(String msg) {
        this();
        this.code = 200;
        this.msg = msg;
    }

    public BaseResult(int code, String message) {
        this();
        this.code = code;
        this.msg = message;
    }

    public BaseResult(int code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public BaseResult(Code code) {
        this();
        this.code = code.getCode();
        this.msg = code.getMsg();
    }
}
