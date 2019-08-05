package com.site.blog.my.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BlogLink {
    /**
     * 友链表主键id
     */
    private Integer linkId;
    /**
     * 友链类别 0-友链 1-推荐 2-个人网站
     */
    private Byte linkType;
    /**
     * 网站名称
     */
    private String linkName;
    /**
     * 网站链接
     */
    private String linkUrl;
    /**
     * 网站描述
     */
    private String linkDescription;
    /**
     * 用于列表排序
     */
    private Integer linkRank;
    /**
     * 是否删除 0-未删除 1-已删除
     */
    private Byte isDeleted;
    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public Integer getLinkId() {
        return linkId;
    }

    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
    }

    public Byte getLinkType() {
        return linkType;
    }

    public void setLinkType(Byte linkType) {
        this.linkType = linkType;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName == null ? null : linkName.trim();
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl == null ? null : linkUrl.trim();
    }

    public String getLinkDescription() {
        return linkDescription;
    }

    public void setLinkDescription(String linkDescription) {
        this.linkDescription = linkDescription == null ? null : linkDescription.trim();
    }

    public Integer getLinkRank() {
        return linkRank;
    }

    public void setLinkRank(Integer linkRank) {
        this.linkRank = linkRank;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", linkId=").append(linkId);
        sb.append(", linkType=").append(linkType);
        sb.append(", linkName=").append(linkName);
        sb.append(", linkUrl=").append(linkUrl);
        sb.append(", linkDescription=").append(linkDescription);
        sb.append(", linkRank=").append(linkRank);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}