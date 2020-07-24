package com.springboot.hibernate.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/6/18 09:44
 */
@Entity
@Table(name = "user")
public class UserBean extends AbstractBaseModel<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(columnDefinition = "varchar(20) " +
            "NOT NULL " +
            "DEFAULT '' " +
            "COMMENT '归属机构ID+用户真实姓名+当前时间hash一下，唯一'")
    private String username;

    @Column(columnDefinition = "int(11) " +
            "NOT NULL " +
            "DEFAULT '0' " +
            "COMMENT '用户所在部门的id'")
    private Integer deptId;

    @Column(columnDefinition = "varchar(200) " +
            "NOT NULL " +
            "DEFAULT '' " +
            "COMMENT '用户所在部门的名字'")
    private String deptName;

    @Column(columnDefinition = "varchar(100) " +
            "NOT NULL " +
            "COMMENT '用户所在层级'")
    private String deptLevel;

    @Column(columnDefinition = "int(11) " +
            "NOT NULL " +
            "DEFAULT '1' " +
            "COMMENT '状态，0：启用 -1：禁用 -2：已拒绝 1：待审核'")
    private Integer status;

    @Column(columnDefinition = "int(11) " +
            "NOT NULL " +
            "COMMENT '登录状态, 0表示已登录、-1表示未登录'")
    private Integer hasLogin = 0;

    @Column(columnDefinition = "int(11) " +
            "NOT NULL " +
            "DEFAULT '0' " +
            "COMMENT '登录次数'")
    private Integer loginNumber;

    @Column(columnDefinition = "varchar(100) " +
            "NOT NULL " +
            "DEFAULT '' " +
            "COMMENT '加密后的密码'")
    private String password;

    @Column(columnDefinition = "varchar(40) " +
            "NOT NULL " +
            "DEFAULT '' " +
            "COMMENT '学历'")
    private String education;

    @Column(columnDefinition = "datetime(3) " +
            "NOT NULL " +
            "DEFAULT CURRENT_TIMESTAMP(3) " +
            "COMMENT '入职时间'")
    private Date entryTime;


    @Column(columnDefinition = "varchar(200) " +
            "COMMENT '头像地址'")
    private String headImgUrl;


    @Column(columnDefinition = "int(11) " +
            "DEFAULT '0' " +
            "COMMENT '巡检总时长'")
    private Integer pollingTime;

    @Column(columnDefinition = "int(11) " +
            "DEFAULT '0' " +
            "COMMENT '巡检总次数'")
    private Integer pollingNum;

    @Column(columnDefinition = "int(11) " +
            "DEFAULT '0' " +
            "COMMENT '事件总次数'")
    private Integer eventNum;

    @Column(columnDefinition = "int(11) " +
            "DEFAULT '0' " +
            "COMMENT '漏检总次数'")
    private Integer forgetNum;

    @Column(columnDefinition = "datetime(3) " +
            "COMMENT '审核时间'")
    private Date examineTime;

    @Column(columnDefinition = "varchar(200) " +
            "COMMENT '驳回原因'")
    private String rejectReason;

    @Column(name = "address", columnDefinition = "varchar(65) not null comment '用户合约地址'")
    private String address;

    @Column(name = "roles", columnDefinition = "varchar(10) comment '用户角色(1，2)'")
    private String roles;

    @Transient
    private String code;

    private Long lastLoginTime = 0L;

    private Integer eventNumMonth = 0;

    private Integer eventNumYear = 0;

    private Double pollingTimeMonth = 0.0;

    private Double pollingTimeYear = 0.0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(String deptLevel) {
        this.deptLevel = deptLevel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getHasLogin() {
        return hasLogin;
    }

    public void setHasLogin(Integer hasLogin) {
        this.hasLogin = hasLogin;
    }

    public Integer getLoginNumber() {
        return loginNumber;
    }

    public void setLoginNumber(Integer loginNumber) {
        this.loginNumber = loginNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Integer getPollingTime() {
        return pollingTime;
    }

    public void setPollingTime(Integer pollingTime) {
        this.pollingTime = pollingTime;
    }

    public Integer getPollingNum() {
        return pollingNum;
    }

    public void setPollingNum(Integer pollingNum) {
        this.pollingNum = pollingNum;
    }

    public Integer getEventNum() {
        return eventNum;
    }

    public void setEventNum(Integer eventNum) {
        this.eventNum = eventNum;
    }

    public Integer getForgetNum() {
        return forgetNum;
    }

    public void setForgetNum(Integer forgetNum) {
        this.forgetNum = forgetNum;
    }

    public Date getExamineTime() {
        return examineTime;
    }

    public void setExamineTime(Date examineTime) {
        this.examineTime = examineTime;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getEventNumMonth() {
        return eventNumMonth;
    }

    public void setEventNumMonth(Integer eventNumMonth) {
        this.eventNumMonth = eventNumMonth;
    }

    public Integer getEventNumYear() {
        return eventNumYear;
    }

    public void setEventNumYear(Integer eventNumYear) {
        this.eventNumYear = eventNumYear;
    }

    public Double getPollingTimeMonth() {
        return pollingTimeMonth;
    }

    public void setPollingTimeMonth(Double pollingTimeMonth) {
        this.pollingTimeMonth = pollingTimeMonth;
    }

    public Double getPollingTimeYear() {
        return pollingTimeYear;
    }

    public void setPollingTimeYear(Double pollingTimeYear) {
        this.pollingTimeYear = pollingTimeYear;
    }
}
