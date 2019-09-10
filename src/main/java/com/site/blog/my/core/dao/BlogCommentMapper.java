package com.site.blog.my.core.dao;

import com.google.common.collect.Lists;
import com.site.blog.my.core.entity.BlogComment;
import com.site.blog.my.core.entity.BlogTag;
import com.site.blog.my.core.util.PageQueryUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
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

    class BlogCommentSqlBuilder {
        public String insertSelective(BlogComment blog) {
            StringBuilder sql = new StringBuilder();
            StringBuilder sqlValues = new StringBuilder("values (");
            sql.append("INSERT INTO tb_blog_comment (");
            if (blog.getCommentId() != null) {
                sql.append("comment_id, ");
                sqlValues.append("#{commentId,jdbcType=BIGINT},");
            }
            if (blog.getBlogId() != null) {
                sql.append("blog_id, ");
                sqlValues.append("#{blogId,jdbcType=BIGINT},");
            }
            if (blog.getCommentator() != null) {
                sql.append("commentator, ");
                sqlValues.append("#{commentator,jdbcType=VARCHAR},");
            }
            if (blog.getEmail() != null) {
                sql.append("email, ");
                sqlValues.append("#{email,jdbcType=VARCHAR},");
            }
            if (blog.getWebsiteUrl() != null) {
                sql.append("website_url,");
                sqlValues.append("#{websiteUrl,jdbcType=VARCHAR},");
            }
            if (blog.getCommentBody() != null) {
                sql.append("comment_body, ");
                sqlValues.append("#{commentBody,jdbcType=VARCHAR},");
            }
            if (blog.getCommentCreateTime() != null) {
                sql.append("comment_create_time, ");
                sqlValues.append("#{commentCreateTime,jdbcType=TIMESTAMP},");
            }
            if (blog.getCommentatorIp() != null) {
                sql.append("commentator_ip, ");
                sqlValues.append("#{commentatorIp,jdbcType=VARCHAR}, ");
            }
            if (blog.getReplyBody() != null) {
                sql.append("reply_body, ");
                sqlValues.append("#{replyBody,jdbcType=VARCHAR},");
            }
            if (blog.getReplyCreateTime() != null) {
                sql.append("reply_create_time, ");
                sqlValues.append("#{replyCreateTime,jdbcType=TIMESTAMP},");
            }
            if (blog.getCommentStatus() != null) {
                sql.append("comment_status, ");
                sqlValues.append("#{commentStatus,jdbcType=TINYINT},");
            }
            if (blog.getIsDeleted() != null) {
                sql.append("is_deleted ) ");
                sqlValues.append("#{isDeleted,jdbcType=TINYINT} )");
            }
            sql.append(sqlValues);
            System.out.println("sql语句===" + sql.toString());
            return sql.toString();
        }

        public String updateByPrimaryKeySelective(BlogComment blog) {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE tb_blog_comment SET ");
            if (blog.getBlogId() != null) {
                sql.append("blog_id = #{blogId,jdbcType=BIGINT},");
            }
            if (blog.getCommentator() != null) {
                sql.append("commentator = #{commentator,jdbcType=VARCHAR},");
            }
            if (blog.getEmail() != null) {
                sql.append("email = #{email,jdbcType=VARCHAR},");
            }
            if (blog.getWebsiteUrl() != null) {
                sql.append("website_url = #{websiteUrl,jdbcType=VARCHAR},");
            }
            if (blog.getCommentBody() != null) {
                sql.append("comment_body = #{commentBody,jdbcType=VARCHAR},");
            }
            if (blog.getCommentCreateTime() != null) {
                sql.append("comment_create_time = #{commentCreateTime,jdbcType=TIMESTAMP},");
            }
            if (blog.getCommentatorIp() != null) {
                sql.append("commentator_ip = #{commentatorIp,jdbcType=VARCHAR}, ");
            }
            if (blog.getReplyBody() != null) {
                sql.append("reply_body = #{replyBody,jdbcType=VARCHAR},");
            }
            if (blog.getReplyCreateTime() != null) {
                sql.append("reply_create_time = #{replyCreateTime,jdbcType=TIMESTAMP},");
            }
            if (blog.getCommentStatus() != null) {
                sql.append("comment_status = #{commentStatus,jdbcType=TINYINT},");
            }
            if (blog.getIsDeleted() != null) {
                sql.append("is_deleted = #{isDeleted,jdbcType=TINYINT} )");
            }
            sql.append("WHERE comment_id = #{commentId,jdbcType=BIGINT}");
            System.out.println("sql语句===" + sql.toString());
            return sql.toString();
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

        public String batchInsertBlogTag(List<BlogTag> tagList) {
            List<String> tagNames = Lists.newArrayList();
            tagList.forEach(n -> tagNames.add(n.getTagName()));
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT into tb_blog_tag(tag_name)");
            sql.append(" VALUES (");
            sql.append(StringUtils.join(tagNames, ",")).append(") ");
            return sql.toString();
        }
    }
}