package com.springboot.core.constant;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/1/3 11:10
 */
public enum FileType {
    UNKNOWN(-1,-1,"未知类型"),
    //钱包操作
    WALLET_STATUS_BLOCKED(0,WalletStatus.BLOCKED.getCode(),"描述一"),
    WALLET_STATUS_LOSS(0,WalletStatus.LOSS.getCode(),"描述一二"),
    WALLET_STATUS_CLOSE(0,WalletStatus.CLOSE.getCode(),"描述三"),
    //交易类别
    TRANS_TRANSFER(1,TransType.TRANS.getCode(),"描述四"),
    TRANS_CASH(1,TransType.CASH.getCode(),"描述五"),
    TRANS_CONVERT(1,TransType.CONVERT.getCode(),"描述六");

    private int opType;
    private int code;

    private String msg;

    FileType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    FileType(int opType, int code, String msg) {
        this.opType = opType;
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

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }

    public static FileType getEnumByCode(Integer code){
        if (null == code){ return UNKNOWN; }
        FileType[] values = FileType.values();
        for (FileType value : values) {
            if (code == value.getCode()){
                return value;
            }
        }
        return UNKNOWN;
    }

    public static FileType getEnumByOpType(Integer opType){
        if (null == opType){ return UNKNOWN; }
        FileType[] values = FileType.values();
        for (FileType value : values) {
            if (opType == value.getOpType()){
                return value;
            }
        }
        return UNKNOWN;
    }
}
