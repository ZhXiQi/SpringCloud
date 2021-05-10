package com.springboot.core.bean.auto;

import com.springboot.core.bean.BaseModel;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class WalletBean extends BaseModel {
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String walletNum;

    private String orgName;

    private String address;

    private BigDecimal balance;

    private String cardNum;

    private Long userId;

    private Byte walletStatus;

    private BigDecimal cardBalance;

    private String accountJson;

    private Byte walletType;

    private String bankName;

    public WalletBean(Long id, LocalDateTime createTime, LocalDateTime updateTime, String walletNum, String orgName, String address, BigDecimal balance, String cardNum, Long userId, Byte walletStatus, BigDecimal cardBalance, String accountJson, Byte walletType, String bankName) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.walletNum = walletNum;
        this.orgName = orgName;
        this.address = address;
        this.balance = balance;
        this.cardNum = cardNum;
        this.userId = userId;
        this.walletStatus = walletStatus;
        this.cardBalance = cardBalance;
        this.accountJson = accountJson;
        this.walletType = walletType;
        this.bankName = bankName;
    }

    public WalletBean() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getWalletNum() {
        return walletNum;
    }

    public void setWalletNum(String walletNum) {
        this.walletNum = walletNum == null ? null : walletNum.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum == null ? null : cardNum.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getWalletStatus() {
        return walletStatus;
    }

    public void setWalletStatus(Byte walletStatus) {
        this.walletStatus = walletStatus;
    }

    public BigDecimal getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(BigDecimal cardBalance) {
        this.cardBalance = cardBalance;
    }

    public String getAccountJson() {
        return accountJson;
    }

    public void setAccountJson(String accountJson) {
        this.accountJson = accountJson == null ? null : accountJson.trim();
    }

    public Byte getWalletType() {
        return walletType;
    }

    public void setWalletType(Byte walletType) {
        this.walletType = walletType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }
}