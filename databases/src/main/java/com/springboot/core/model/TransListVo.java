package com.springboot.core.model;

import com.springboot.core.constant.TransType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/11/30 13:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransListVo {

    private long txId;

    private long serialNum;

    private String txHash;

    private String orgName;

    private String address;

    private int typeCode;

    private String type;

    private double money;

    private String dateTime;

    private String fromOrgName;

    private String fromWalletNum;

    private String fromWalletAddr;

    private String toOrgName;

    private String toWalletNum;

    private String toWalletAddr;

    private String remark;


    public void setType(int type) {
        this.typeCode = type;
        this.type = TransType.getMsgByCode(type);
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
