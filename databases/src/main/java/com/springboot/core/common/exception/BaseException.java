package com.springboot.core.common.exception;

import com.springboot.core.common.response.BaseResult;

/**
 *
 * 基本异常
 */
public abstract class BaseException extends RuntimeException {
    private int codeInt;
    private String msg;
    private BaseResult baseResult;

    public BaseException() {
        super();
    }

    public BaseException(int codeInt, String msg) {
        super(msg);
        this.baseResult = new BaseResult(codeInt,msg);
    }

    public int getCodeInt() {
        return codeInt;
    }

    public void setCodeInt(int codeInt) {
        this.codeInt = codeInt;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BaseResult getBaseResult() {
        return baseResult;
    }

    public void setBaseResult(BaseResult baseResult) {
        this.baseResult = baseResult;
    }
}
