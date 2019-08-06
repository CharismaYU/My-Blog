package com.site.blog.my.core.entity;

public class BlogTag extends AbstractCreated {
    /**
     * 标签表主键id
     */
    private Integer tagId;
    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 是否删除 0=否 1=是
     */
    private Byte isDeleted;


    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }
}