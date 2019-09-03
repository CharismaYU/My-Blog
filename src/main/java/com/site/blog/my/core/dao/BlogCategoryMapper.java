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
import org.springframework.stereotype.Component;

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

    class BlogCategorySqlBuilder {
        public String insertSelective(BlogCategory blog) {
            StringBuilder sql = new StringBuilder();
            StringBuilder sqlValues = new StringBuilder("values (");
            sql.append("insert into tb_blog_category (");
            if (blog.getCategoryId() != null) {
                sql.append("category_id, ");
                sqlValues.append("#{categoryId,jdbcType=INTEGER},");
            }
            if (blog.getCategoryName() != null) {
                sql.append("category_name, ");
                sqlValues.append("#{categoryName,jdbcType=VARCHAR},");
            }
            if (blog.getCategoryIcon() != null) {
                sql.append("category_icon, ");
                sqlValues.append("#{categoryIcon,jdbcType=VARCHAR},");
            }
            if (blog.getCategoryRank() != null) {
                sql.append("category_rank, ");
                sqlValues.append("#{categoryRank,jdbcType=INTEGER},");
            }
            if (blog.getIsDeleted() != null) {
                sql.append("is_deleted, ");
                sqlValues.append("#{isDeleted,jdbcType=TINYINT},");
            }
            if (blog.getCreateTime() != null) {
                sql.append("create_time) ");
                sqlValues.append("#{createTime,jdbcType=TIMESTAMP} )");
            }
            sql.append(sqlValues);
            System.out.println("sql语句===" + sql.toString());
            return sql.toString();
        }

        public String updateByPrimaryKeySelective(BlogCategory blog) {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE tb_blog_category SET ");
            if (blog.getCategoryName() != null) {
                sql.append("category_name = #{categoryName,jdbcType=VARCHAR},");
            }
            if (blog.getCategoryIcon() != null) {
                sql.append("category_icon = #{categoryIcon,jdbcType=VARCHAR},");
            }
            if (blog.getCategoryRank() != null) {
                sql.append("category_rank = #{categoryRank,jdbcType=INTEGER},");
            }
            if (blog.getIsDeleted() != null) {
                sql.append("is_deleted = #{isDeleted,jdbcType=TINYINT},");
            }
            if (blog.getCreateTime() != null) {
                sql.append("create_time = #{createTime,jdbcType=TIMESTAMP} ");
            }
            sql.append("WHERE category_id = #{categoryId,jdbcType=INTEGER}");
            System.out.println("sql语句===" + sql.toString());
            return sql.toString();
        }

        public String findCategoryList(final PageQueryUtil pageUtil) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from tb_blog_category where IS_DELETED=0");
            sql.append(" order by category_rank desc,create_time desc");
            if (pageUtil.get("start") != null && pageUtil.getLimit() > 0)
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