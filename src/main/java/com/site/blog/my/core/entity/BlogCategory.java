package com.site.blog.my.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BlogCategory extends AbstractCreated {
    /**
     * 分类表主键
     */
    private Integer categoryId;
    /**
     * 分类的名称
     */
    private String categoryName;
    /**
     * 分类的图标
     */
    private String categoryIcon;
    /**
     * 分类的排序值 被使用的越多数值越大
     */
    private Integer categoryRank;
    /**
     * 是否删除 0=否 1=是
     */
    private Byte isDeleted;


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon == null ? null : categoryIcon.trim();
    }

    public Integer getCategoryRank() {
        return categoryRank;
    }

    public void setCategoryRank(Integer categoryRank) {
        this.categoryRank = categoryRank;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }
}