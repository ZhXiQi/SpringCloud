package com.springcloud.service.constant;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/5/27 15:56
 */
public enum Code {
    PARAMETER_ERROR(0000,"参数错误"),
    UNKNOWN_ABNORMAL(1111,"未知异常"),
    VALID_ERROR(2222,"校验异常"),
    JSON_TRANSFER_ERROR(3333,"数据传输错误")
    ;

    private int code;

    private String msg;

    Code(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
