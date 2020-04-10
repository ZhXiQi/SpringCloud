package com.springboot.core.constant;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/6 16:53
 */
public enum UserType {

    ORG(0,"企业用户"),
    ADMIN(1,"管理员");

    private int code;
    private String msg;

    UserType(int code, String msg) {
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
