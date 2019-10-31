package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.BlogComment;
import com.site.blog.my.core.util.PageQueryUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface BlogCommentMapper {

    @Update("update tb_blog_comment set is_deleted=1 where comment_id = #{commentId,jdbcType=BIGINT} and is_deleted=0")
    int deleteByPrimaryKey(Long commentId);

    @Insert("insert into tb_blog_comment (comment_id, blog_id, commentator, " +
            "      email, website_url, comment_body, " +
            "      comment_create_time, commentator_ip, reply_body, " +
            "      reply_create_time, comment_status, is_deleted" +
            "      )" +
            "    values (#{commentId,jdbcType=BIGINT}, #{blogId,jdbcType=BIGINT}, #{commentator,jdbcType=VARCHAR}, " +
            "      #{email,jdbcType=VARCHAR}, #{websiteUrl,jdbcType=VARCHAR}, #{commentBody,jdbcType=VARCHAR}, " +
            "      #{commentCreateTime,jdbcType=TIMESTAMP}, #{commentatorIp,jdbcType=VARCHAR}, #{replyBody,jdbcType=VARCHAR}, " +
            "      #{replyCreateTime,jdbcType=TIMESTAMP}, #{commentStatus,jdbcType=TINYINT}, #{isDeleted,jdbcType=TINYINT}" +
            "      )")
    int insert(BlogComment record);

    @InsertProvider(type = BlogCommentSqlBuilder.class, method = "insertSelective")
    int insertSelective(BlogComment record);

    @Select("select * from from tb_blog_comment where comment_id = #{commentId,jdbcType=BIGINT} and is_deleted=0")
    BlogComment selectByPrimaryKey(Long commentId);

    @UpdateProvider(type = BlogCommentSqlBuilder.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BlogComment record);

    @Update("update tb_blog_comment" +
            " set blog_id = #{blogId,jdbcType=BIGINT}," +
            "  commentator = #{commentator,jdbcType=VARCHAR}," +
            "  email = #{email,jdbcType=VARCHAR}," +
            "  website_url = #{websiteUrl,jdbcType=VARCHAR}," +
            "  comment_body = #{commentBody,jdbcType=VARCHAR}," +
            "  comment_create_time = #{commentCreateTime,jdbcType=TIMESTAMP}," +
            "  commentator_ip = #{commentatorIp,jdbcType=VARCHAR}," +
            "  reply_body = #{replyBody,jdbcType=VARCHAR}," +
            "  reply_create_time = #{replyCreateTime,jdbcType=TIMESTAMP}," +
            "  comment_status = #{commentStatus,jdbcType=TINYINT}," +
            "  is_deleted = #{isDeleted,jdbcType=TINYINT}" +
            " where comment_id = #{commentId,jdbcType=BIGINT}")
    int updateByPrimaryKey(BlogComment record);

    @SelectProvider(type = BlogCommentSqlBuilder.class, method = "findBlogCommentList")
    List<BlogComment> findBlogCommentList(PageQueryUtil pageUtil);

    @SelectProvider(type = BlogCommentSqlBuilder.class, method = "getTotalBlogComments")
    int getTotalBlogComments(Map map);

    @UpdateProvider(type = BlogCommentSqlBuilder.class, method = "checkDone")
    int checkDone(Integer[] ids);

    @UpdateProvider(type = BlogCommentSqlBuilder.class, method = "deleteBatch")
    int deleteBatch(Integer[] ids);

    class BlogCommentSqlBuilder extends SQL {

        private static final String TABLE_NAME = "tb_blog_comment";

        public String insertSelective(BlogComment blog) {
            String sql = new SQL() {{
                INSERT_INTO(TABLE_NAME);
                if (blog.getCommentId() != null) {
                    VALUES("comment_id", "#{commentId,jdbcType=BIGINT}");
                }
                if (blog.getBlogId() != null) {
                    VALUES("blog_id", "#{blogId,jdbcType=BIGINT}");
                }
                if (blog.getCommentator() != null) {
                    VALUES("commentator", "#{commentator,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(blog.getEmail())) {
                    VALUES("email", "#{email,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(blog.getWebsiteUrl())) {
                    VALUES("website_url", "#{websiteUrl,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(blog.getCommentBody())) {
                    VALUES("comment_body", "#{commentBody,jdbcType=VARCHAR}");
                }
                if (blog.getCommentCreateTime() != null) {
                    VALUES("comment_create_time", "#{commentCreateTime,jdbcType=TIMESTAMP}");
                }
                if (StringUtils.isNotBlank(blog.getCommentatorIp())) {
                    VALUES("commentator_ip", "#{commentatorIp,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(blog.getReplyBody())) {
                    VALUES("reply_body", "#{replyBody,jdbcType=VARCHAR}");
                }
                if (blog.getReplyCreateTime() != null) {
                    VALUES("reply_create_time", "#{replyCreateTime,jdbcType=TIMESTAMP}");
                }
                if (blog.getCommentStatus() != null) {
                    VALUES("comment_status", "#{commentStatus,jdbcType=TINYINT}");
                }
                if (blog.getIsDeleted() != null) {
                    VALUES("is_deleted", "#{isDeleted,jdbcType=TINYINT}");
                }
            }}.toString();
            System.out.println("sql语句===" + sql);
            return sql;
        }

        public String updateByPrimaryKeySelective(BlogComment blog) {
            String sql = new SQL() {{
                UPDATE(TABLE_NAME);
                if (blog.getBlogId() != null) {
                    SET("blog_id = #{blogId,jdbcType=BIGINT}");
                }
                if (blog.getCommentator() != null) {
                    SET("commentator = #{commentator,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(blog.getEmail())) {
                    SET("email = #{email,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(blog.getWebsiteUrl())) {
                    SET("website_url = #{websiteUrl,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(blog.getCommentBody())) {
                    SET("comment_body = #{commentBody,jdbcType=VARCHAR}");
                }
                if (blog.getCommentCreateTime() != null) {
                    SET("comment_create_time = #{commentCreateTime,jdbcType=TIMESTAMP}");
                }
                if (StringUtils.isNotBlank(blog.getCommentatorIp())) {
                    SET("commentator_ip = #{commentatorIp,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(blog.getReplyBody())) {
                    SET("reply_body = #{replyBody,jdbcType=VARCHAR}");
                }
                if (blog.getReplyCreateTime() != null) {
                    SET("reply_create_time = #{replyCreateTime,jdbcType=TIMESTAMP}");
                }
                if (blog.getCommentStatus() != null) {
                    SET("comment_status = #{commentStatus,jdbcType=TINYINT}");
                }
                if (blog.getIsDeleted() != null) {
                    SET("is_deleted = #{isDeleted,jdbcType=TINYINT} )");
                }
                WHERE("comment_id = #{commentId,jdbcType=BIGINT}");
            }}.toString();
            System.out.println("sql语句===" + sql);
            return sql;
        }

        public String findBlogCommentList(final PageQueryUtil pageUtil) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT * FROM tb_blog_comment WHERE is_deleted=0");
            if (pageUtil.get("blogId") != null) {
                sql.append(" AND blog_id = #{blogId}");
            }
            if (pageUtil.get("commentStatus") != null) {
                sql.append(" AND comment_status = #{commentStatus}");
            }
            sql.append(" ORDER BY comment_id desc");
            if (pageUtil != null && pageUtil.get("start") != null && pageUtil.get("limit") != null)
                sql.append(" limit #{start},#{limit}");
            System.out.println("查询sql==" + sql.toString());
            return sql.toString();
        }

        public String getTotalBlogComments(final Map<String, Object> pageUtil) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT count(*) FROM tb_blog_comment WHERE is_deleted=0");
            if (pageUtil != null) {
                if (pageUtil.get("blogId") != null) {
                    sql.append(" AND blog_id = #{blogId}");
                }
                if (pageUtil.get("commentStatus") != null) {
                    sql.append(" AND comment_status = #{commentStatus}");
                }
            }

            System.out.println("查询sql==" + sql.toString());
            return sql.toString();
        }

        public String checkDone(Integer[] ids) {
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE tb_blog_tag SET comment_status=1 WHERE comment_id IN(");
            sql.append(StringUtils.join(ids, ",")).append(") and comment_status = 0");
            return sql.toString();
        }

        //删除的方法
        public String deleteBatch(Integer[] ids) {
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE tb_blog_comment SET is_deleted=1 WHERE comment_id IN(");
            sql.append(StringUtils.join(ids, ",")).append(") ");
            return sql.toString();
        }

    }
}