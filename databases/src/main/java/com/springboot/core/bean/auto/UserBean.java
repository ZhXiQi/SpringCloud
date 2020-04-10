package com.springboot.core.bean.auto;

import com.springboot.core.bean.BaseModel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class UserBean extends BaseModel {
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Byte userType;

    private String email;

    private String orgName;

    private String socialCreditCode;

    private String url;

    private String orgAddr;

    private String currentAddr;

    private String userPwd;

    private String userName;

    public UserBean(Long id, LocalDateTime createTime, LocalDateTime updateTime, Byte userType, String email, String orgName, String socialCreditCode, String url, String orgAddr, String currentAddr, String userPwd, String userName) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.userType = userType;
        this.email = email;
        this.orgName = orgName;
        this.socialCreditCode = socialCreditCode;
        this.url = url;
        this.orgAddr = orgAddr;
        this.currentAddr = currentAddr;
        this.userPwd = userPwd;
        this.userName = userName;
    }

    public UserBean() {
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

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode == null ? null : socialCreditCode.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getOrgAddr() {
        return orgAddr;
    }

    public void setOrgAddr(String orgAddr) {
        this.orgAddr = orgAddr == null ? null : orgAddr.trim();
    }

    public String getCurrentAddr() {
        return currentAddr;
    }

    public void setCurrentAddr(String currentAddr) {
        this.currentAddr = currentAddr == null ? null : currentAddr.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
}