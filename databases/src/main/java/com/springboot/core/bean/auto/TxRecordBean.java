package com.springboot.core.bean.auto;

import com.springboot.core.bean.BaseModel;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class TxRecordBean extends BaseModel {
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String txHash;

    private String hash;

    private Integer blockNum;

    private String toAddress;

    private String fromAddress;

    private BigDecimal money;

    private String remark;

    private Integer handleTime;

    private Integer status;

    private Integer type;

    private Long serialNum;

    private String fromCardNum;

    private String toCardNum;

    private BigDecimal fromBalance;

    private BigDecimal toBalance;

    public TxRecordBean(Long id, LocalDateTime createTime, LocalDateTime updateTime, String txHash, String hash, Integer blockNum, String toAddress, String fromAddress, BigDecimal money, String remark, Integer handleTime, Integer status, Integer type, Long serialNum, String fromCardNum, String toCardNum, BigDecimal fromBalance, BigDecimal toBalance) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.txHash = txHash;
        this.hash = hash;
        this.blockNum = blockNum;
        this.toAddress = toAddress;
        this.fromAddress = fromAddress;
        this.money = money;
        this.remark = remark;
        this.handleTime = handleTime;
        this.status = status;
        this.type = type;
        this.serialNum = serialNum;
        this.fromCardNum = fromCardNum;
        this.toCardNum = toCardNum;
        this.fromBalance = fromBalance;
        this.toBalance = toBalance;
    }

    public TxRecordBean() {
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

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash == null ? null : txHash.trim();
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash == null ? null : hash.trim();
    }

    public Integer getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(Integer blockNum) {
        this.blockNum = blockNum;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress == null ? null : toAddress.trim();
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress == null ? null : fromAddress.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Integer handleTime) {
        this.handleTime = handleTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Long serialNum) {
        this.serialNum = serialNum;
    }

    public String getFromCardNum() {
        return fromCardNum;
    }

    public void setFromCardNum(String fromCardNum) {
        this.fromCardNum = fromCardNum == null ? null : fromCardNum.trim();
    }

    public String getToCardNum() {
        return toCardNum;
    }

    public void setToCardNum(String toCardNum) {
        this.toCardNum = toCardNum == null ? null : toCardNum.trim();
    }

    public BigDecimal getFromBalance() {
        return fromBalance;
    }

    public void setFromBalance(BigDecimal fromBalance) {
        this.fromBalance = fromBalance;
    }

    public BigDecimal getToBalance() {
        return toBalance;
    }

    public void setToBalance(BigDecimal toBalance) {
        this.toBalance = toBalance;
    }
}