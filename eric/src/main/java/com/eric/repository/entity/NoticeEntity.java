package com.eric.repository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NoticeEntity {

    @JsonProperty("createBy")
    public String createBy;
    @JsonProperty("createTime")
    private String createTime;
    @JsonProperty("updateBy")
    private String updateBy;
    @JsonProperty("updateTime")
    private String updateTime;
    @JsonProperty("remark")
    private String remark;
    @JsonProperty("noticeId")
    private String noticeId;
    @JsonProperty("tenantId")
    private String tenantId;
    @JsonProperty("tenantName")
    private String tenantName;
    @JsonProperty("publish")
    private String publish;
    @JsonProperty("publishTime")
    private String publishTime;
    @JsonProperty("activeTime")
    private String activeTime;
    @JsonProperty("expireTime")
    private String expireTime;
    @JsonProperty("noticeTitle")
    public String noticeTitle;
    @JsonProperty("noticeType")
    private String noticeType;
    @JsonProperty("noticeContent")
    public String noticeContent;
    @JsonProperty("content")
    public String content;
    @JsonProperty("status")
    private String status;
    @JsonProperty("contentList")
    private Object contentList;



    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Object getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getContentList() {
        return contentList;
    }

    public void setContentList(Object contentList) {
        this.contentList = contentList;
    }

}
