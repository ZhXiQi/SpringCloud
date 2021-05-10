package com.springboot.core.constant;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/30 16:48
 */
public enum TxStatus {
    UNKNOWN(-1,"异常交易"),
    WAITING_BANK(1,"等待银行转账"),
    WAITING_CHAIN(2,"等待区块链转账"),
    WAITING_BANK_COMPLETE(3,"等待银行卡转账完成"),
    WAITING_CHAIN_COMPLETE(4,"等待区块链转账完成"),
    COMPLETE(10,"交易完成");

    private int code;

    private String msg;

    TxStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getMsgByCode(int code){
        TxStatus[] txStatuses = TxStatus.values();
        for (TxStatus txStatus : txStatuses) {
            if (txStatus.getCode() == code){
                return txStatus.getMsg();
            }
        }
        return UNKNOWN.getMsg();
    }
}
