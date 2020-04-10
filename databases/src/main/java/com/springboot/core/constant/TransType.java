package com.springboot.core.constant;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/11/30 13:52
 */
public enum TransType {
    IN(0,"转入"),
    OUT(1,"转出"),
    TRANS(2,"转账"),
    //币换成钱 兑付
    CASH(3,"提现"),
    //钱换成币 兑换
    CONVERT(4,"充值"),
    CASH_TRANS(5,"提现申请"),
    CONVERT_TRANS(6,"充值申请");

    private int code;

    private String msg;

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

    TransType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(int code){
        TransType[] values = TransType.values();
        String result = "";
        for (TransType type : values) {
            if (code == type.getCode()){
                return type.getMsg();
            }
        }
        return result;
    }
}
