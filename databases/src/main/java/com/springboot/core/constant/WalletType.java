package com.springboot.core.constant;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/30 19:32
 */
public enum WalletType {

    UNKNOWN(-1,"未知类型"),
    RMB(0,"人民币"),
    DOLLAR(1,"美元");

    private int code;
    private String msg;

    WalletType(int code, String msg) {
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

    public static String getMsgByCode(int code){
        WalletType[] walletTypes = WalletType.values();
        for (WalletType walletType : walletTypes) {
            if (walletType.getCode() == code){
                return walletType.getMsg();
            }
        }
        return UNKNOWN.getMsg();
    }
}
