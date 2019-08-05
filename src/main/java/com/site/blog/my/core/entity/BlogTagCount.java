package com.site.blog.my.core.entity;

public class BlogTagCount {
    /**
     * 标签表主键id
     */
    private Integer tagId;
    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 标签数
     */
    private Integer tagCount;


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
        this.tagName = tagName;
    }

    public Integer getTagCount() {
        return tagCount;
    }

    public void setTagCount(Integer tagCount) {
        this.tagCount = tagCount;
    }
}
