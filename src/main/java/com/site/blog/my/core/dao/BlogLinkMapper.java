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
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.util.StringUtil;

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

    class BlogLinkSqlBuilder {
        public String insertSelective(BlogLink blogLink) {
            StringBuilder sql = new StringBuilder();
            StringBuilder sqlValues = new StringBuilder(" values (");
            sql.append("INSERT INTO tb_link (");
            if (blogLink.getLinkId() != null) {
                sql.append("link_id, ");
                sqlValues.append("#{linkId,jdbcType=INTEGER},");
            }
            if (blogLink.getLinkType() != null) {
                sql.append("link_type, ");
                sqlValues.append("#{linkType,jdbcType=TINYINT},");
            }
            if (blogLink.getLinkName() != null) {
                sql.append("link_name, ");
                sqlValues.append("#{linkName,jdbcType=VARCHAR},");
            }
            if (blogLink.getLinkUrl() != null) {
                sql.append("link_url, ");
                sqlValues.append("#{linkUrl,jdbcType=VARCHAR},");
            }
            if (blogLink.getLinkDescription() != null) {
                sql.append("link_description, ");
                sqlValues.append("#{linkDescription,jdbcType=VARCHAR},");
            }
            if (blogLink.getLinkRank() != null) {
                sql.append("linkRank, ");
                sqlValues.append("#{linkRank,jdbcType=INTEGER} , ");
            }
            if (blogLink.getIsDeleted() != null) {
                sql.append("is_deleted, ");
                sqlValues.append("#{isDeleted,jdbcType=TINYINT} , ");
            }
            if (blogLink.getCreateTime() != null) {
                sql.append("create_time) ");
                sqlValues.append("#{createTime,jdbcType=TIMESTAMP} )");
            }
            sql.append(sqlValues);
            System.out.println("sql语句===" + sql.toString());
            return sql.toString();
        }

        public String updateByPrimaryKeySelective(BlogLink blogLink) {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE tb_link SET ");
            if (blogLink.getLinkType() != null) {
                sql.append(" link_type = #{linkType,jdbcType=TINYINT}, ");
            }
            if (StringUtil.isNotEmpty(blogLink.getLinkName())) {
                sql.append(" link_name = #{linkName,jdbcType=VARCHAR}, ");
            }
            if (StringUtil.isNotEmpty(blogLink.getLinkUrl())) {
                sql.append(" link_url = #{linkUrl,jdbcType=VARCHAR}, ");
            }
            if (StringUtil.isNotEmpty(blogLink.getLinkDescription())) {
                sql.append(" link_description = #{linkDescription,jdbcType=VARCHAR}, ");
            }
            if (blogLink.getLinkRank() != null) {
                sql.append(" link_rank = #{linkRank,jdbcType=INTEGER}, ");
            }
            if (blogLink.getIsDeleted() != null) {
                sql.append(" is_deleted = #{isDeleted,jdbcType=TINYINT}, ");
            }
            if (blogLink.getCreateTime() != null) {
                sql.append(" create_time = #{createTime,jdbcType=TIMESTAMP} ");
            }
            sql.append("  WHERE link_id = #{linkId,jdbcType=INTEGER}");
            return sql.toString();
        }

        public String findLinkList(final PageQueryUtil params) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT * from tb_link WHERE 1=1 AND is_deleted=0");
            sql.append(" ORDER BY link_id DESC");
            if (params.get("start") != null && params.get("limit") != null) {
                sql.append(" limit ").append(params.get("start")).append(",").append(params.get("limit"));
            }
            System.out.println("查询sql==" + sql.toString());
            return sql.toString();
        }

        //删除的方法
        public String deleteBatch(@Param("ids") final String[] ids) {
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE tb_link SET is_deleted=1 WHERE link_id IN( ");
            sql.append(StringUtils.join(ids, ",")).append(") ");
            return sql.toString();
        }
    }
}