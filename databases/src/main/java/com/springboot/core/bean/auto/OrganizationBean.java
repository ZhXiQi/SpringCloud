package com.springboot.core.bean.auto;

import com.springboot.core.bean.BaseModel;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class OrganizationBean extends BaseModel {
    private Integer id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String address;

    private String cardNum;

    private String bankName;

    private BigDecimal bankMoney;

    private BigDecimal chainMoney;

    private String orgName;

    private String orgDesc;

    private String accountJson;

    public OrganizationBean(Integer id, LocalDateTime createTime, LocalDateTime updateTime, String address, String cardNum, String bankName, BigDecimal bankMoney, BigDecimal chainMoney, String orgName, String orgDesc, String accountJson) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.address = address;
        this.cardNum = cardNum;
        this.bankName = bankName;
        this.bankMoney = bankMoney;
        this.chainMoney = chainMoney;
        this.orgName = orgName;
        this.orgDesc = orgDesc;
        this.accountJson = accountJson;
    }

    public OrganizationBean() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum == null ? null : cardNum.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public BigDecimal getBankMoney() {
        return bankMoney;
    }

    public void setBankMoney(BigDecimal bankMoney) {
        this.bankMoney = bankMoney;
    }

    public BigDecimal getChainMoney() {
        return chainMoney;
    }

    public void setChainMoney(BigDecimal chainMoney) {
        this.chainMoney = chainMoney;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgDesc() {
        return orgDesc;
    }

    public void setOrgDesc(String orgDesc) {
        this.orgDesc = orgDesc == null ? null : orgDesc.trim();
    }

    public String getAccountJson() {
        return accountJson;
    }

    public void setAccountJson(String accountJson) {
        this.accountJson = accountJson == null ? null : accountJson.trim();
    }
}