package com.springboot.core.bean.auto;

import com.springboot.core.bean.BaseModel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class WalletRecordBean extends BaseModel {
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long walletId;

    private String opName;

    private Byte opStatus;

    private String remark;

    public WalletRecordBean(Long id, LocalDateTime createTime, LocalDateTime updateTime, Long walletId, String opName, Byte opStatus, String remark) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.walletId = walletId;
        this.opName = opName;
        this.opStatus = opStatus;
        this.remark = remark;
    }

    public WalletRecordBean() {
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

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName == null ? null : opName.trim();
    }

    public Byte getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(Byte opStatus) {
        this.opStatus = opStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}