package com.site.blog.my.core.entity;

public class Blog extends AbstractAuditable {
    /**
     * 博客表主键id
     */
    private Long blogId;
    /**
     * 博客标题
     */
    private String blogTitle;
    /**
     * 博客自定义路径url
     */
    private String blogSubUrl;
    /**
     * 博客封面图
     */
    private String blogCoverImage;
    /**
     * 博客分类id
     */
    private Integer blogCategoryId;
    /**
     * 博客分类(冗余字段)
     */
    private String blogCategoryName;
    /**
     * 博客标签
     */
    private String blogTags;
    /**
     * 0-草稿 1-发布
     */
    private Byte blogStatus;
    /**
     * 阅读量
     */
    private Long blogViews;
    /**
     * 0-允许评论 1-不允许评论
     */
    private Byte enableComment;
    /**
     * 是否删除 0=否 1=是
     */
    private Byte isDeleted;
    /**
     * 博客内容
     */
    private String blogContent;

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle == null ? null : blogTitle.trim();
    }

    public String getBlogSubUrl() {
        return blogSubUrl;
    }

    public void setBlogSubUrl(String blogSubUrl) {
        this.blogSubUrl = blogSubUrl == null ? null : blogSubUrl.trim();
    }

    public String getBlogCoverImage() {
        return blogCoverImage;
    }

    public void setBlogCoverImage(String blogCoverImage) {
        this.blogCoverImage = blogCoverImage == null ? null : blogCoverImage.trim();
    }

    public Integer getBlogCategoryId() {
        return blogCategoryId;
    }

    public void setBlogCategoryId(Integer blogCategoryId) {
        this.blogCategoryId = blogCategoryId;
    }

    public String getBlogCategoryName() {
        return blogCategoryName;
    }

    public void setBlogCategoryName(String blogCategoryName) {
        this.blogCategoryName = blogCategoryName == null ? null : blogCategoryName.trim();
    }

    public String getBlogTags() {
        return blogTags;
    }

    public void setBlogTags(String blogTags) {
        this.blogTags = blogTags == null ? null : blogTags.trim();
    }

    public Byte getBlogStatus() {
        return blogStatus;
    }

    public void setBlogStatus(Byte blogStatus) {
        this.blogStatus = blogStatus;
    }

    public Long getBlogViews() {
        return blogViews;
    }

    public void setBlogViews(Long blogViews) {
        this.blogViews = blogViews;
    }

    public Byte getEnableComment() {
        return enableComment;
    }

    public void setEnableComment(Byte enableComment) {
        this.enableComment = enableComment;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent == null ? null : blogContent.trim();
    }

}