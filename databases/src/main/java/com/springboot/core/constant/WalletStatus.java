package com.springboot.core.constant;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/12/9 21:31
 */
public enum  WalletStatus {
    INIT(0,"初始化"),
    NORMAL(1,"正常"),
    BLOCKED(2,"冻结"),
    LOSS(3,"挂失"),
    CLOSE(4,"销户");

    private int code;

    private String msg;

    WalletStatus(int code, String msg) {
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
        WalletStatus[] values = WalletStatus.values();
        String result = "";
        for (WalletStatus type : values) {
            if (code == type.getCode()){
                return type.getMsg();
            }
        }
        return result;
    }

    public static WalletStatus getEnumByCode(int code) throws Exception {
        WalletStatus[] values = WalletStatus.values();
        for (WalletStatus walletStatus: values){
            if (code == walletStatus.getCode()){
                return walletStatus;
            }
        }
        throw new Exception("钱包账户状态无此code码信息");
    }
}
