package com.springboot.core.bean.auto;

import com.springboot.core.bean.BaseModel;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class BankReceiptBean extends BaseModel {
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long txId;

    private BigDecimal money;

    private String remark;

    private Integer handleTime;

    private String txHash;

    private String hash;

    private Integer blockNum;

    private Integer status;

    private String serialNum;

    private String fromCardNum;

    private String toCardNum;

    private Integer type;

    public BankReceiptBean(Long id, LocalDateTime createTime, LocalDateTime updateTime, Long txId, BigDecimal money, String remark, Integer handleTime, String txHash, String hash, Integer blockNum, Integer status, String serialNum, String fromCardNum, String toCardNum, Integer type) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.txId = txId;
        this.money = money;
        this.remark = remark;
        this.handleTime = handleTime;
        this.txHash = txHash;
        this.hash = hash;
        this.blockNum = blockNum;
        this.status = status;
        this.serialNum = serialNum;
        this.fromCardNum = fromCardNum;
        this.toCardNum = toCardNum;
        this.type = type;
    }

    public BankReceiptBean() {
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

    public Long getTxId() {
        return txId;
    }

    public void setTxId(Long txId) {
        this.txId = txId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum == null ? null : serialNum.trim();
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}