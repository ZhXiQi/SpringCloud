package com.springboot.hibernate.bean;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;


@MappedSuperclass
public abstract class AbstractBaseModel<ID extends Serializable> {

    private static final long serialVersionUID = 1195969732659409799L;

    @Column(columnDefinition = "int default 0")
    private int creator = 0;

    @Column(updatable = false, columnDefinition = "datetime(3) " +
            "NOT NULL " +
            "DEFAULT CURRENT_TIMESTAMP(3) " +
            "COMMENT '创建时间'")
    private Date createTime;

    @Column(columnDefinition = "int default 0")
    private int updator = 0;

    @Column(columnDefinition = "datetime(3) " +
            "COMMENT '最近修改时间'")
    private Date operateTime;

    @Column(columnDefinition = "varchar(200) " +
            "COMMENT '备注'")
    private String remark;

    @Version
    private int version;

    @PrePersist
    public void setInsertBefore(){
        this.createTime = new Date();
        this.operateTime = new Date();

    }

    @PreUpdate
    public void setUpdateBefore(){
        this.operateTime = new Date();
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getUpdator() {
        return updator;
    }

    public void setUpdator(int updator) {
        this.updator = updator;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}

