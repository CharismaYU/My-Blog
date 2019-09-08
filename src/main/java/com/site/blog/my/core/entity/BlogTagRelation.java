package com.site.blog.my.core.entity;

public class BlogTagRelation extends AbstractCreated {
    /**
     * 关系表id
     */
    private Long relationId;
    /**
     * 博客id
     */
    private Long blogId;
    /**
     * 标签id
     */
    private Integer tagId;


    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

}