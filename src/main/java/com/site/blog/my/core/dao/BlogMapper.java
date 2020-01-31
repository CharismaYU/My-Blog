package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.Blog;
import com.site.blog.my.core.util.PageQueryUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
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
            "      #{updateTime,jdbcType=TIMESTAMP}, #{blogContent,jdbcType=VARCHAR})")
    int insert(Blog record);

    @InsertProvider(type = BlogSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyProperty = "blogId")
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
            "      blog_content = #{blogContent,jdbcType=VARCHAR}" +
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

    @SelectProvider(type = BlogSqlProvider.class, method = "getBlogsPageByTagId")
    List<Blog> getBlogsPageByTagId(PageQueryUtil pageUtil);

    @Select("SELECT COUNT(*) FROM tb_blog WHERE  blog_id IN (SELECT blog_id FROM tb_blog_tag_relation WHERE tag_id = #{tagId}) AND blog_status =1 AND is_deleted=0")
    int getTotalBlogsByTagId(PageQueryUtil pageUtil);

    @Select("SELECT * FROM tb_blog WHERE blog_sub_url = #{subUrl,jdbcType=VARCHAR} and is_deleted = 0 limit 1")
    Blog selectBySubUrl(String subUrl);

    int updateBlogCategorys(@Param("categoryName") String categoryName, @Param("categoryId") Integer categoryId, @Param("ids") Integer[] ids);

    class BlogSqlProvider extends SQL {

        private static final String TABLE_NAME = "TB_BLOG";

        public String insertSelective(Blog blog) {
            String sql = new SQL() {{
                INSERT_INTO(TABLE_NAME);
                if (blog.getBlogId() != null) {
                    VALUES("blog_id", "#{blogId,jdbcType=BIGINT}");
                }
                if (blog.getBlogTitle() != null) {
                    VALUES("blog_title", "#{blogTitle,jdbcType=VARCHAR}");
                }
                if (blog.getBlogSubUrl() != null) {
                    VALUES("blog_sub_url", "#{blogSubUrl,jdbcType=VARCHAR}");
                }
                if (blog.getBlogCoverImage() != null) {
                    VALUES("blog_cover_image", "#{blogCoverImage,jdbcType=VARCHAR}");
                }
                if (blog.getBlogCategoryId() != null) {
                    VALUES("blog_category_id", "#{blogCategoryId,jdbcType=VARCHAR}");
                }
                if (blog.getBlogCategoryName() != null) {
                    VALUES("blog_category_name", "#{blogCategoryName,jdbcType=VARCHAR}");
                }
                if (blog.getBlogTags() != null) {
                    VALUES("blog_tags", "#{blogTags,jdbcType=VARCHAR}");
                }
                if (blog.getBlogStatus() != null) {
                    VALUES("blog_status", "#{blogStatus,jdbcType=TINYINT}");
                }
                if (blog.getBlogViews() != null) {
                    VALUES("blog_views", "#{blogViews,jdbcType=BIGINT}");
                }
                if (blog.getEnableComment() != null) {
                    VALUES("enable_comment", "#{enableComment,jdbcType=TINYINT}");
                }
                if (blog.getIsDeleted() != null) {
                    VALUES("is_deleted", "#{isDeleted,jdbcType=TINYINT}");
                }
                if (blog.getCreateTime() != null) {
                    VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
                }
                if (blog.getUpdateTime() != null) {
                    VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
                }
                if (blog.getBlogContent() != null) {
                    VALUES("blog_content", "#{blogContent,jdbcType=VARCHAR}");
                }

            }}.toString();
            System.out.println("sql语句===" + sql);
            return sql;
        }

        public String updateByPrimaryKeySelective(Blog blog) {
            String sql = new SQL() {{
                UPDATE(TABLE_NAME);
                if (blog.getBlogId() != null) {
                    SET("blog_id = #{blogId,jdbcType=BIGINT}");
                }
                if (blog.getBlogTitle() != null) {
                    SET("blog_title = #{blogTitle,jdbcType=VARCHAR}");
                }
                if (blog.getBlogSubUrl() != null) {
                    SET("blog_sub_url = #{blogSubUrl,jdbcType=VARCHAR}");
                }
                if (blog.getBlogCoverImage() != null) {
                    SET("blog_cover_image = #{blogCoverImage,jdbcType=VARCHAR}");
                }
                if (blog.getBlogCategoryId() != null) {
                    SET("blog_category_id = #{blogCategoryId,jdbcType=VARCHAR}");
                }
                if (blog.getBlogCategoryName() != null) {
                    SET("blog_category_name = #{blogCategoryName,jdbcType=VARCHAR}");
                }
                if (blog.getBlogTags() != null) {
                    SET("blog_tags = #{blogTags,jdbcType=VARCHAR}");
                }
                if (blog.getBlogStatus() != null) {
                    SET("blog_status = #{blogStatus,jdbcType=TINYINT}");
                }
                if (blog.getBlogViews() != null) {
                    SET("blog_views = #{blogViews,jdbcType=BIGINT}");
                }
                if (blog.getEnableComment() != null) {
                    SET("enable_comment = #{enableComment,jdbcType=TINYINT}");
                }
                if (blog.getIsDeleted() != null) {
                    SET("is_deleted = #{isDeleted,jdbcType=TINYINT}");
                }
                if (blog.getCreateTime() != null) {
                    SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
                }
                if (blog.getUpdateTime() != null) {
                    SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
                }
                if (blog.getBlogContent() != null) {
                    SET("blog_content = #{blogContent,jdbcType=VARCHAR}");
                }
                WHERE("blog_id = #{blogId,jdbcType=BIGINT}");

            }}.toString();
            System.out.println("sql语句===" + sql);
            return sql;
        }

        public String findBlogList(final PageQueryUtil params) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from TB_BLOG where 1=1 and IS_DELETED=0");
            if (params != null) {
                if (!StringUtils.isEmpty((String) params.get("keyword"))) {
                    sql.append(" and (blog_title like '%").append((String) params.get("author")).append("%' ");
                    sql.append(" or blog_category_name like '%").append((String) params.get("author")).append("%' )");
                }
                if (params.get("blogStatus") != null) {
                    sql.append(" and blog_status = '").append(params.get("blogStatus")).append("'");
                }
                if (params.get("blogCategoryId") != null) {
                    sql.append(" and blog_category_id = '").append(params.get("blogCategoryId")).append("'");
                }
            }
            sql.append(" order by blog_id desc");
            if (params != null) {
                if (params.get("start") != null && params.get("limit") != null) {
                    sql.append(" limit ").append(params.get("start")).append(",").append(params.get("limit"));
                }
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
            if (params != null) {
                if (params.get("keyword") != null) {
                    sql.append(" and (blog_title like '%").append((String) params.get("author")).append("%' ");
                    sql.append(" or blog_category_name like '%").append((String) params.get("author")).append("%' )");
                }
                if (params.get("blogStatus") != null) {
                    sql.append(" and blog_status = '").append(params.get("blogStatus")).append("'");
                }
                if (params.get("blogCategoryId") != null) {
                    sql.append(" and blog_category_id = '").append(params.get("blogCategoryId")).append("'");
                }
            }
            System.out.println("查询sql==" + sql.toString());
            return sql.toString();
        }

        //删除的方法
        public String deleteBatch(@Param("ids") final String[] ids) {
            StringBuffer sql = new StringBuffer();
            sql.append("DELETE TB_BLOG WHERE blog_id IN( ");
            sql.append(StringUtils.join(ids, ",")).append(") ");
            return sql.toString();
        }

        public String getBlogsPageByTagId(PageQueryUtil pageUtil) {
            StringBuffer sql = new StringBuffer();
            sql.append("Select * ");
            sql.append(" FROM tb_blog WHERE blog_id IN (SELECT blog_id FROM tb_blog_tag_relation WHERE tag_id = #{tagId}) AND blog_status =1 AND is_deleted=0 order by blog_id desc ");
            if (pageUtil != null && pageUtil.get("start") != null && pageUtil.getLimit() > 0)
                sql.append("limit #{start},#{limit}");
            return sql.toString();
        }
    }

}