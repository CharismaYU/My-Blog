package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.Blog;
import com.site.blog.my.core.util.PageQueryUtil;
import com.site.blog.my.core.util.StringUtil;
import org.apache.ibatis.annotations.DeleteProvider;
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

/**
 * Component注解不添加也没事，只是不加service那边引入LearnMapper会有错误提示，但不影响
 */
@Component
@Mapper
public interface BlogMapper {

    @Update("update tb_blog set is_deleted = 1 where blog_id = #{blogId,jdbcType=BIGINT} and is_deleted = 0")
    int deleteByPrimaryKey(Long blogId);

    @Insert("insert into tb_blog (blog_id, blog_title, blog_sub_url, " +
            "      blog_cover_image, blog_category_id, blog_category_name, " +
            "      blog_tags, blog_status, blog_views, " +
            "      enable_comment, is_deleted, create_time, " +
            "      update_time, blog_content)" +
            "    values (#{blogId,jdbcType=BIGINT}, #{blogTitle,jdbcType=VARCHAR}, #{blogSubUrl,jdbcType=VARCHAR}, " +
            "      #{blogCoverImage,jdbcType=VARCHAR}, #{blogCategoryId,jdbcType=INTEGER}, #{blogCategoryName,jdbcType=VARCHAR}, " +
            "      #{blogTags,jdbcType=VARCHAR}, #{blogStatus,jdbcType=TINYINT}, #{blogViews,jdbcType=BIGINT}, " +
            "      #{enableComment,jdbcType=TINYINT}, #{isDeleted,jdbcType=TINYINT}, #{createTime,jdbcType=TIMESTAMP}, " +
            "      #{updateTime,jdbcType=TIMESTAMP}, #{blogContent,jdbcType=LONGVARCHAR})")
    int insert(Blog record);

    @InsertProvider(type = BlogSqlProvider.class, method = "insertSelective")
    int insertSelective(Blog record);

    @Select("select * from tb_blog  where blog_id = #{blogId,jdbcType=BIGINT} and is_deleted = 0")
    Blog selectByPrimaryKey(Long blogId);

    @UpdateProvider(type = BlogSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Blog record);

    @Update("    update tb_blog" +
            "    set blog_title = #{blogTitle,jdbcType=VARCHAR}," +
            "      blog_sub_url = #{blogSubUrl,jdbcType=VARCHAR}," +
            "      blog_cover_image = #{blogCoverImage,jdbcType=VARCHAR}," +
            "      blog_category_id = #{blogCategoryId,jdbcType=INTEGER}," +
            "      blog_category_name = #{blogCategoryName,jdbcType=VARCHAR}," +
            "      blog_tags = #{blogTags,jdbcType=VARCHAR}," +
            "      blog_status = #{blogStatus,jdbcType=TINYINT}," +
            "      blog_views = #{blogViews,jdbcType=BIGINT}," +
            "      enable_comment = #{enableComment,jdbcType=TINYINT}," +
            "      is_deleted = #{isDeleted,jdbcType=TINYINT}," +
            "      create_time = #{createTime,jdbcType=TIMESTAMP}," +
            "      update_time = #{updateTime,jdbcType=TIMESTAMP}," +
            "      blog_content = #{blogContent,jdbcType=LONGVARCHAR}" +
            "    where blog_id = #{blogId,jdbcType=BIGINT}")
    int updateByPrimaryKeyWithBLOBs(Blog record);

    @Update("update tb_blog" +
            "    set blog_title = #{blogTitle,jdbcType=VARCHAR}," +
            "      blog_sub_url = #{blogSubUrl,jdbcType=VARCHAR}," +
            "      blog_cover_image = #{blogCoverImage,jdbcType=VARCHAR}," +
            "      blog_category_id = #{blogCategoryId,jdbcType=INTEGER}," +
            "      blog_category_name = #{blogCategoryName,jdbcType=VARCHAR}," +
            "      blog_tags = #{blogTags,jdbcType=VARCHAR}," +
            "      blog_status = #{blogStatus,jdbcType=TINYINT}," +
            "      blog_views = #{blogViews,jdbcType=BIGINT}," +
            "      enable_comment = #{enableComment,jdbcType=TINYINT}," +
            "      is_deleted = #{isDeleted,jdbcType=TINYINT}," +
            "      create_time = #{createTime,jdbcType=TIMESTAMP}," +
            "      update_time = #{updateTime,jdbcType=TIMESTAMP}" +
            "    where blog_id = #{blogId,jdbcType=BIGINT}")
    int updateByPrimaryKey(Blog record);

    @SelectProvider(type = BlogSqlProvider.class, method = "findBlogList")
    List<Blog> findBlogList(PageQueryUtil pageUtil);

    @SelectProvider(type = BlogSqlProvider.class, method = "findBlogListByType")
    List<Blog> findBlogListByType(@Param("type") int type, @Param("limit") int limit);

    @SelectProvider(type = BlogSqlProvider.class, method = "getTotalBlogs")
    int getTotalBlogs(PageQueryUtil pageUtil);

    @DeleteProvider(type = BlogSqlProvider.class, method = "deleteBatch")
    int deleteBatch(Integer[] ids);


    List<Blog> getBlogsPageByTagId(PageQueryUtil pageUtil);

    @Select("SELECT COUNT(*) FROM tb_blog WHERE  blog_id IN (SELECT blog_id FROM tb_blog_tag_relation WHERE tag_id = #{tagId}) AND blog_status =1 AND is_deleted=0")
    int getTotalBlogsByTagId(PageQueryUtil pageUtil);

    @Select("SELECT * FROM tb_blog WHERE blog_sub_url = #{subUrl,jdbcType=VARCHAR} and is_deleted = 0 limit 1")
    Blog selectBySubUrl(String subUrl);

    int updateBlogCategorys(@Param("categoryName") String categoryName, @Param("categoryId") Integer categoryId, @Param("ids") Integer[] ids);

    class BlogSqlProvider {
        public String insertSelective(Blog blog) {
            StringBuilder sql = new StringBuilder();
            StringBuilder sqlValues = new StringBuilder("values (");
            sql.append("insert into tb_blog (");
            if (blog.getBlogId() != null) {
                sql.append("blogId, ");
                sqlValues.append("#{blogId,jdbcType=BIGINT},");
            }
            if (blog.getBlogTitle() != null) {
                sql.append("blog_title, ");
                sqlValues.append("#{blogTitle,jdbcType=VARCHAR},");
            }
            if (blog.getBlogSubUrl() != null) {
                sql.append("blog_sub_url, ");
                sqlValues.append("#{blogSubUrl,jdbcType=VARCHAR},");
            }
            if (blog.getBlogCoverImage() != null) {
                sql.append("blog_cover_image, ");
                sqlValues.append("#{blogCoverImage,jdbcType=VARCHAR},");
            }
            if (blog.getBlogCategoryId() != null) {
                sql.append("blog_category_id, ");
                sqlValues.append("#{blogCategoryId,jdbcType=VARCHAR},");
            }
            if (blog.getBlogCategoryName() != null) {
                sql.append("blog_category_name, ");
                sqlValues.append("#{blogCategoryName,jdbcType=VARCHAR},");
            }
            if (blog.getBlogTags() != null) {
                sql.append("blog_tags, ");
                sqlValues.append("#{blogTags,jdbcType=VARCHAR},");
            }
            if (blog.getBlogStatus() != null) {
                sql.append("blog_status, ");
                sqlValues.append("#{blogStatus,jdbcType=TINYINT},");
            }
            if (blog.getBlogViews() != null) {
                sql.append("blog_views, ");
                sqlValues.append("#{blogViews,jdbcType=BIGINT},");
            }
            if (blog.getEnableComment() != null) {
                sql.append("enable_comment, ");
                sqlValues.append("#{enableComment,jdbcType=TINYINT},");
            }
            if (blog.getIsDeleted() != null) {
                sql.append("is_deleted, ");
                sqlValues.append("#{isDeleted,jdbcType=TINYINT},");
            }
            if (blog.getCreateTime() != null) {
                sql.append("create_time, ");
                sqlValues.append("#{createTime,jdbcType=TIMESTAMP},");
            }
            if (blog.getUpdateTime() != null) {
                sql.append("update_time, ");
                sqlValues.append("#{updateTime,jdbcType=TIMESTAMP},");
            }
            if (blog.getBlogContent() != null) {
                sql.append("blog_content )");
                sqlValues.append("#{blogContent,jdbcType=LONGVARCHAR} )");
            }
            sql.append(sqlValues);
            System.out.println("sql语句===" + sql.toString());
            return sql.toString();
        }

        public String updateByPrimaryKeySelective(Blog blog) {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE TB_BLOG SET ");
            if (blog.getBlogId() != null) {
                sql.append("blogId = #{blogId,jdbcType=BIGINT},");
            }
            if (blog.getBlogTitle() != null) {
                sql.append("blog_title = #{blogTitle,jdbcType=VARCHAR},");
            }
            if (blog.getBlogSubUrl() != null) {
                sql.append("blog_sub_url = #{blogSubUrl,jdbcType=VARCHAR},");
            }
            if (blog.getBlogCoverImage() != null) {
                sql.append("blog_cover_image = #{blogCoverImage,jdbcType=VARCHAR},");
            }
            if (blog.getBlogCategoryId() != null) {
                sql.append("blog_category_id = #{blogCategoryId,jdbcType=VARCHAR},");
            }
            if (blog.getBlogCategoryName() != null) {
                sql.append("blog_category_name = #{blogCategoryName,jdbcType=VARCHAR},");
            }
            if (blog.getBlogTags() != null) {
                sql.append("blog_tags = #{blogTags,jdbcType=VARCHAR},");
            }
            if (blog.getBlogStatus() != null) {
                sql.append("blog_status = #{blogStatus,jdbcType=TINYINT},");
            }
            if (blog.getBlogViews() != null) {
                sql.append("blog_views = #{blogViews,jdbcType=BIGINT},");
            }
            if (blog.getEnableComment() != null) {
                sql.append("enable_comment = #{enableComment,jdbcType=TINYINT},");
            }
            if (blog.getIsDeleted() != null) {
                sql.append("is_deleted = #{isDeleted,jdbcType=TINYINT},");
            }
            if (blog.getCreateTime() != null) {
                sql.append("create_time = #{createTime,jdbcType=TIMESTAMP},");
            }
            if (blog.getUpdateTime() != null) {
                sql.append("update_time = #{updateTime,jdbcType=TIMESTAMP},");
            }
            if (blog.getBlogContent() != null) {
                sql.append("blog_content = #{blogContent,jdbcType=LONGVARCHAR} ");
            }
            sql.append("WHERE blog_id = #{blogId,jdbcType=BIGINT}");
            System.out.println("sql语句===" + sql.toString());
            return sql.toString();
        }

        public String findBlogList(final PageQueryUtil params) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from TB_BLOG where 1=1 and IS_DELETED=0");
            if (!StringUtil.isNull((String) params.get("keyword"))) {
                sql.append(" and (blog_title like '%").append((String) params.get("author")).append("%' ");
                sql.append(" or blog_category_name like '%").append((String) params.get("author")).append("%' )");
            }
            if (params.get("blogStatus") != null) {
                sql.append(" and blog_status = '").append(params.get("blogStatus")).append("'");
            }
            if (params.get("blogCategoryId") != null) {
                sql.append(" and blog_category_id = '").append(params.get("blogCategoryId")).append("'");
            }
            sql.append(" order by blog_id desc");
            if (params.get("start") != null && params.get("limit") != null) {
                sql.append(" limit ").append(params.get("start")).append(",").append(params.get("limit"));
            }
            System.out.println("查询sql==" + sql.toString());
            return sql.toString();
        }

        public String findBlogListByType(final int type, final int limit) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from TB_BLOG where 1=1 and IS_DELETED=0 AND blog_status = 1 ");
            if (type == 0) {
                sql.append(" order by blog_views desc ");
            } else if (type == 1) {
                sql.append(" order by blog_id desc");
            }
            sql.append(" limit ").append(limit);
            System.out.println("查询sql==" + sql.toString());
            return sql.toString();
        }

        public String getTotalBlogs(final PageQueryUtil params) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(*) from tb_blog where IS_DELETED=0");
            if (!StringUtil.isNull((String) params.get("keyword"))) {
                sql.append(" and (blog_title like '%").append((String) params.get("author")).append("%' ");
                sql.append(" or blog_category_name like '%").append((String) params.get("author")).append("%' )");
            }
            if (params.get("blogStatus") != null) {
                sql.append(" and blog_status = '").append(params.get("blogStatus")).append("'");
            }
            if (params.get("blogCategoryId") != null) {
                sql.append(" and blog_category_id = '").append(params.get("blogCategoryId")).append("'");
            }
            System.out.println("查询sql==" + sql.toString());
            return sql.toString();
        }

        //删除的方法
        public String deleteBatch(@Param("ids") final String[] ids) {
            StringBuffer sql = new StringBuffer();
            sql.append("DELETE TB_BLOG WHERE blog_id IN( ");
            for (int i = 0; i < ids.length; i++) {
                if (i == ids.length - 1) {
                    sql.append(ids[i]);
                } else {
                    sql.append(ids[i]).append(",");
                }
            }
            sql.append(" )");
            return sql.toString();
        }

        public String getBlogsPageByTagId(PageQueryUtil pageUtil) {
            StringBuffer sql = new StringBuffer();
            sql.append("Select * ");
            sql.append(" FROM tb_blog WHERE blog_id IN (SELECT blog_id FROM tb_blog_tag_relation WHERE tag_id = #{tagId}) AND blog_status =1 AND is_deleted=0 order by blog_id desc ");
            if (pageUtil.get("start") != null && pageUtil.getLimit() > 0)
                sql.append("limit #{start},#{limit}");
            return sql.toString();
        }
    }

}