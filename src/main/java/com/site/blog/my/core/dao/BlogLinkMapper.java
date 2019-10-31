package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.BlogLink;
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
public interface BlogLinkMapper {
    @Update("UPDATE tb_link SET is_deleted = 1 WHERE link_id = #{linkId,jdbcType=INTEGER} AND is_deleted = 0")
    int deleteByPrimaryKey(Integer linkId);

    @Insert(" insert into tb_link (link_id, link_type, link_name, " +
            "      link_url, link_description, link_rank, " +
            "      is_deleted, create_time)" +
            "    values (#{linkId,jdbcType=INTEGER}, #{linkType,jdbcType=TINYINT}, #{linkName,jdbcType=VARCHAR}, " +
            "      #{linkUrl,jdbcType=VARCHAR}, #{linkDescription,jdbcType=VARCHAR}, #{linkRank,jdbcType=INTEGER}, " +
            "      #{isDeleted,jdbcType=TINYINT}, #{createTime,jdbcType=TIMESTAMP})")
    int insert(BlogLink record);

    @InsertProvider(type = BlogLinkSqlBuilder.class, method = "insertSelective")
    int insertSelective(BlogLink blogLink);

    @Select(" SELECT * FROM tb_link WHERE link_id = #{linkId,jdbcType=INTEGER} AND is_deleted = 0")
    BlogLink selectByPrimaryKey(Integer linkId);

    @UpdateProvider(type = BlogLinkSqlBuilder.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BlogLink record);

    @Update("update tb_link" +
            "    set link_type = #{linkType,jdbcType=TINYINT}," +
            "      link_name = #{linkName,jdbcType=VARCHAR}," +
            "      link_url = #{linkUrl,jdbcType=VARCHAR}," +
            "      link_description = #{linkDescription,jdbcType=VARCHAR}," +
            "      link_rank = #{linkRank,jdbcType=INTEGER}," +
            "      is_deleted = #{isDeleted,jdbcType=TINYINT}," +
            "      create_time = #{createTime,jdbcType=TIMESTAMP}" +
            "    where link_id = #{linkId,jdbcType=INTEGER}")
    int updateByPrimaryKey(BlogLink record);

    @SelectProvider(type = BlogLinkSqlBuilder.class, method = "findLinkList")
    List<BlogLink> findLinkList(PageQueryUtil pageUtil);

    @Select("SELECT count(*) FROM tb_link WHERE is_deleted=0")
    int getTotalLinks(PageQueryUtil pageUtil);

    @UpdateProvider(type = BlogLinkSqlBuilder.class, method = "deleteBatch")
    int deleteBatch(Integer[] ids);

    class BlogLinkSqlBuilder extends SQL {
        private static final String TABLE_NAME = "tb_link";

        public String insertSelective(BlogLink blogLink) {
            String sql = new SQL() {{
                INSERT_INTO(TABLE_NAME);
                if (blogLink.getLinkId() != null) {
                    VALUES("link_id", "#{linkId,jdbcType=INTEGER}");
                }
                if (blogLink.getLinkType() != null) {
                    VALUES("link_type", "#{linkType,jdbcType=TINYINT}");
                }
                if (StringUtils.isNotBlank(blogLink.getLinkName())) {
                    VALUES("link_name", "#{linkName,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(blogLink.getLinkUrl())) {
                    VALUES("link_url", "#{linkUrl,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(blogLink.getLinkDescription())) {
                    VALUES("link_description", "#{linkDescription,jdbcType=VARCHAR}");
                }
                if (blogLink.getLinkRank() != null) {
                    VALUES("link_rank", "#{linkRank,jdbcType=INTEGER}");
                }
                if (blogLink.getIsDeleted() != null) {
                    VALUES("is_deleted", "#{isDeleted,jdbcType=TINYINT}");
                }
                if (blogLink.getCreateTime() == null) {
                    blogLink.setCreateTime(new Date());
                }
                VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
            }}.toString();
            System.out.println("sql语句===" + sql);
            return sql;
        }

        public String updateByPrimaryKeySelective(BlogLink blogLink) {
            String sql = new SQL() {{
                UPDATE(TABLE_NAME);
                if (blogLink.getLinkType() != null) {
                    SET(" link_type = #{linkType,jdbcType=TINYINT}");
                }
                if (StringUtils.isNotBlank(blogLink.getLinkName())) {
                    SET(" link_name = #{linkName,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(blogLink.getLinkUrl())) {
                    SET(" link_url = #{linkUrl,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(blogLink.getLinkDescription())) {
                    SET(" link_description = #{linkDescription,jdbcType=VARCHAR}");
                }
                if (blogLink.getLinkRank() != null) {
                    SET(" link_rank = #{linkRank,jdbcType=INTEGER}");
                }
                if (blogLink.getIsDeleted() != null) {
                    SET(" is_deleted = #{isDeleted,jdbcType=TINYINT}");
                }
                if (blogLink.getCreateTime() != null) {
                    SET(" create_time = #{createTime,jdbcType=TIMESTAMP}");
                }
                WHERE("link_id = #{linkId,jdbcType=INTEGER}");
            }}.toString();
            return sql;
        }

        public String findLinkList(final PageQueryUtil params) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT * from tb_link WHERE 1=1 AND is_deleted=0");
            sql.append(" ORDER BY link_id DESC");
            if (params != null) {
                if (params.get("start") != null && params.get("limit") != null) {
                    sql.append(" limit ").append(params.get("start")).append(",").append(params.get("limit"));
                }
            }
            System.out.println("查询sql==" + sql.toString());
            return sql.toString();
        }

        //删除的方法
        public String deleteBatch(@Param("ids") final Integer[] ids) {
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE tb_link SET is_deleted=1 WHERE link_id IN( ");
            sql.append(StringUtils.join(ids, ",")).append(") ");
            return sql.toString();
        }
    }
}