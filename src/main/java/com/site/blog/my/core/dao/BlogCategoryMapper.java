package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.BlogCategory;
import com.site.blog.my.core.util.PageQueryUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Mapper
public interface BlogCategoryMapper {

    @Update("UPDATE tb_blog_category SET is_deleted = 1 where category_id = #{categoryId,jdbcType=VARCHAR} AND is_deleted = 0")
    int deleteByPrimaryKey(Integer categoryId);

    @Insert("insert into tb_blog_category (category_id, category_name, category_icon, " +
            "  category_rank, is_deleted, create_time )" +
            "  values (#{categoryId,jdbcType=INTEGER}, #{categoryName,jdbcType=VARCHAR}, #{categoryIcon,jdbcType=VARCHAR}, " +
            "  #{categoryRank,jdbcType=INTEGER}, #{isDeleted,jdbcType=TINYINT}, #{createTime,jdbcType=TIMESTAMP})")
    int insert(BlogCategory record);

    @InsertProvider(type = BlogCategorySqlBuilder.class, method = "insertSelective")
    int insertSelective(BlogCategory record);

    @Select("select * from tb_blog_category where category_id = #{categoryId,jdbcType=INTEGER} AND is_deleted = 0")
    BlogCategory selectByPrimaryKey(Integer categoryId);

    @Select("select * from tb_blog_category where category_name = #{categoryName,jdbcType=VARCHAR} AND is_deleted = 0")
    BlogCategory selectByCategoryName(String categoryName);

    @UpdateProvider(type = BlogCategorySqlBuilder.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BlogCategory record);

    @Update("update tb_blog_category" +
            "    set category_name = #{categoryName,jdbcType=VARCHAR}," +
            "      category_icon = #{categoryIcon,jdbcType=VARCHAR}," +
            "      category_rank = #{categoryRank,jdbcType=INTEGER}," +
            "      is_deleted = #{isDeleted,jdbcType=TINYINT}," +
            "      create_time = #{createTime,jdbcType=TIMESTAMP}" +
            "    where category_id = #{categoryId,jdbcType=INTEGER}")
    int updateByPrimaryKey(BlogCategory record);

    @SelectProvider(type = BlogCategorySqlBuilder.class, method = "findCategoryList")
    List<BlogCategory> findCategoryList(PageQueryUtil pageUtil);

    @SelectProvider(type = BlogCategorySqlBuilder.class, method = "selectByCategoryIds")
    List<BlogCategory> selectByCategoryIds(@Param("categoryIds") List<Integer> categoryIds);

    @Select("select count(*) from tb_blog_category where is_deleted=0")
    int getTotalCategories(PageQueryUtil pageUtil);

    @UpdateProvider(type = BlogCategorySqlBuilder.class, method = "deleteBatch")
    int deleteBatch(Integer[] ids);

    class BlogCategorySqlBuilder extends SQL {

        private static final String TABLE_NAME = "tb_blog_category";

        public String insertSelective(BlogCategory blog) {
            String sql = new SQL() {{
                INSERT_INTO(TABLE_NAME);
                if (blog.getCategoryId() != null) {
                    VALUES("category_id", "#{categoryId,jdbcType=INTEGER}");
                }
                if (StringUtils.isNotBlank(blog.getCategoryName())) {
                    VALUES("category_name", "#{categoryName,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(blog.getCategoryIcon())) {
                    VALUES("category_icon", "#{categoryIcon,jdbcType=VARCHAR}");
                }
                if (blog.getCategoryRank() != null) {
                    VALUES("category_rank", "#{categoryRank,jdbcType=INTEGER}");
                }
                if (blog.getIsDeleted() != null) {
                    VALUES("is_deleted", "#{isDeleted,jdbcType=TINYINT}");
                }
                if (blog.getCreateTime() == null) {
                    blog.setCreateTime(new Date());
                }
                VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
            }}.toString();
            System.out.println("sql语句===" + sql);
            return sql;
        }

        public String updateByPrimaryKeySelective(BlogCategory blog) {

            String sql = new SQL() {{
                UPDATE(TABLE_NAME);
                if (StringUtils.isNotBlank(blog.getCategoryName())) {
                    SET("category_name = #{categoryName,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(blog.getCategoryIcon())) {
                    SET("category_icon = #{categoryIcon,jdbcType=VARCHAR}");
                }
                if (blog.getCategoryRank() != null) {
                    SET("category_rank = #{categoryRank,jdbcType=INTEGER}");
                }
                if (blog.getIsDeleted() != null) {
                    SET("is_deleted = #{isDeleted,jdbcType=TINYINT}");
                }
                WHERE("category_id = #{categoryId,jdbcType=INTEGER}");
            }}.toString();
            System.out.println("sql语句===" + sql);
            return sql;
        }

        public String findCategoryList(final PageQueryUtil pageUtil) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from tb_blog_category where IS_DELETED=0");
            sql.append(" order by category_rank desc,create_time desc");
            if (pageUtil != null && pageUtil.get("start") != null && pageUtil.getLimit() > 0)
                sql.append(" limit #{start},#{limit}");
            System.out.println("查询sql==" + sql.toString());
            return sql.toString();
        }

        //删除的方法
        public String deleteBatch(Integer[] ids) {
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE tb_blog_category SET is_deleted=1 WHERE category_id IN(");
            sql.append(StringUtils.join(ids, ",")).append(") ");
            return sql.toString();
        }

        public String selectByCategoryIds(List<Integer> categoryIds) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT * FROM tb_blog_category WHERE category_id IN(");
            sql.append(StringUtils.join(categoryIds, ",")).append(") ");
            return sql.toString();
        }
    }
}