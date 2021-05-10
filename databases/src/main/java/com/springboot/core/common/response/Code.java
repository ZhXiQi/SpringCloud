package com.springboot.core.common.response;


public enum Code {
    //通用部分
    UNDEFINED(-1, "未定义"),
    SUCCESS(0, "成功"),
    ERROR(1, "失败"),
    OP_ERROR(2,"操作失败"),

    UNAUTH_USER(1000, "权限拒绝"),
    NETWORK_ERROR(1001, "余额不足"),
    ENV_FILE_ERROR(1002, "网络紧张，请重新尝试交易"),

    DATA_NOT_EXIST(1003, "暂无数据"),
    NODE_EXCUTE_ERROR(1004, "node执行appjs失败"),
    FILE_HASH_ERROR(1005, "获取文件hash失败"),

    USER_NOT_EXIST(1006,"用户不存在"),
    REG_USER_FAIL(1007,"注册失败"),
    EMAIL_EXISTED(1008,"此邮箱已注册"),
    PWD_ERROR(1009,"密码错误"),


    OSS_ERROR(2001, "oss上传文件异常"),
    WALLET_ERROR(2002,"创建钱包失败"),
    WALLET_NOT_EXIST(2003,"钱包账户不存在"),
    WALLET_CANNOT_OP(2004,"账户当前状态不可进行此操作"),
    WALLET_INIT(2005,"账户初始化，请先审核"),
    UNKNOWN_WALLET_STATUS(2006,"未知状态"),

    TRACK_EMPTY_ERROR(3001, "表中没有取证记录"),
    ADD_ORG_ERROR(3002,"添加商户信息失败"),

    FLORESCENCE_FALL(4001, "取证失败"),

    TX_Extra_set(8001, "失败"),
    TX_Extra_get(8002, "失败"),
    BLOCK_CHAIN_ERROR(8003,"出了点小差"),
    TRANS_NOT_EXIST(8004,"交易不存在"),
    TX_STATUS_ERROR(8005,"当前交易状态不允许进行此操作"),

    //SQL相关异常
    SQL_DUPLICATE_ERROR(9001,"SQL主键重复")
    ;

    private int code;
    private String msg;

    // 构造方法
    Code(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public static String getMsgByCodeInt(int codeInt) {
        for (Code e : Code.values()) {
            if (e.getCode() == codeInt) {
                return e.msg;
            }
        }
        throw new IllegalArgumentException("未定义的code码:" + codeInt);
    }

    public static Code getCodeByCodeInt(int codeInt) {
        for (Code code : Code.values()) {
            if (code.getCode() == codeInt) {
                return code;
            }
        }
        throw new IllegalArgumentException("未定义的code码:" + codeInt);
    }

}
