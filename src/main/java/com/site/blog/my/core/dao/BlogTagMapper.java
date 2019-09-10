package com.site.blog.my.core.dao;

import com.google.common.collect.Lists;
import com.site.blog.my.core.entity.BlogTag;
import com.site.blog.my.core.entity.BlogTagCount;
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

@Component
@Mapper
public interface BlogTagMapper {
    @Update("UPDATE tb_blog_tag SET is_deleted = 1 WHERE tag_id = #{tagId,jdbcType=INTEGER}")
    int deleteByPrimaryKey(Integer tagId);

    @Insert("insert into tb_blog_tag (tag_id, tag_name, is_deleted, create_time)" +
            " values (#{tagId,jdbcType=INTEGER}, #{tagName,jdbcType=VARCHAR}, #{isDeleted,jdbcType=TINYINT}, " +
            " #{createTime,jdbcType=TIMESTAMP})")
    int insert(BlogTag record);

    @InsertProvider(type = BlogTagSqlBuilder.class, method = "insertSelective")
    int insertSelective(BlogTag record);

    @Select("select * from tb_blog_tag where tag_id = #{tagId,jdbcType=INTEGER} AND is_deleted = 0")
    BlogTag selectByPrimaryKey(Integer tagId);

    @Select("select * from tb_blog_tag where tag_name = #{tagName,jdbcType=VARCHAR} AND is_deleted = 0")
    BlogTag selectByTagName(String tagName);

    @UpdateProvider(type = BlogTagSqlBuilder.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BlogTag record);

    @Update("update tb_blog_tag" +
            "  set tag_name = #{tagName,jdbcType=VARCHAR}," +
            "  is_deleted = #{isDeleted,jdbcType=TINYINT}," +
            "  create_time = #{createTime,jdbcType=TIMESTAMP}" +
            "  where tag_id = #{tagId,jdbcType=INTEGER}")
    int updateByPrimaryKey(BlogTag record);

    @SelectProvider(type = BlogTagSqlBuilder.class, method = "findTagList")
    List<BlogTag> findTagList(PageQueryUtil pageUtil);

    @Select("SELECT t_r.*,t.tag_name FROM (SELECT r.tag_id,r.tag_count FROM (SELECT tag_id ,COUNT(*) AS tag_count FROM" +
            " (SELECT tr.tag_id FROM tb_blog_tag_relation tr LEFT JOIN tb_blog b ON tr.blog_id = b.blog_id WHERE b.is_deleted=0)" +
            "  trb GROUP BY tag_id) r ORDER BY tag_count DESC LIMIT 20 ) AS t_r LEFT JOIN tb_blog_tag t ON t_r.tag_id = t.tag_id WHERE t.is_deleted=0")
    List<BlogTagCount> getTagCount();

    @Select("select count(*)  from tb_blog_tag where is_deleted=0 ")
    int getTotalTags(PageQueryUtil pageUtil);

    @UpdateProvider(type = BlogTagSqlBuilder.class, method = "deleteBatch")
    int deleteBatch(Integer[] ids);

    @UpdateProvider(type = BlogTagSqlBuilder.class, method = "batchInsertBlogTag")
    int batchInsertBlogTag(List<BlogTag> tagList);

    class BlogTagSqlBuilder {
        public String insertSelective(BlogTag blog) {
            StringBuilder sql = new StringBuilder();
            StringBuilder sqlValues = new StringBuilder("values (");
            sql.append("INSERT INTO tb_blog_tag (");
            if (blog.getTagId() != null) {
                sql.append("tag_id, ");
                sqlValues.append("#{tagId,jdbcType=INTEGER},");
            }
            if (blog.getTagName() != null) {
                sql.append("tag_name, ");
                sqlValues.append("#{tagName,jdbcType=VARCHAR},");
            }
            if (blog.getIsDeleted() != null) {
                sql.append("is_deleted, ");
                sqlValues.append("#{categoryIcon,jdbcType=VARCHAR},");
            }
            if (blog.getCreateTime() != null) {
                sql.append("create_time) ");
                sqlValues.append("#{createTime,jdbcType=TIMESTAMP} )");
            }
            sql.append(sqlValues);
            System.out.println("sql语句===" + sql.toString());
            return sql.toString();
        }

        public String updateByPrimaryKeySelective(BlogTag blog) {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE tb_blog_tag SET ");
            if (blog.getTagName() != null) {
                sql.append("tag_name = #{tagName,jdbcType=VARCHAR},");
            }
            if (blog.getIsDeleted() != null) {
                sql.append("is_deleted = #{isDeleted,jdbcType=TINYINT},");
            }
            if (blog.getCreateTime() != null) {
                sql.append("create_time = #{createTime,jdbcType=TIMESTAMP} ");
            }
            sql.append("WHERE tag_id = #{tagId,jdbcType=INTEGER}");
            System.out.println("sql语句===" + sql.toString());
            return sql.toString();
        }

        public String findTagList(final PageQueryUtil pageUtil) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from tb_blog_tag where IS_DELETED=0");
            sql.append(" order by tag_id desc");
            if (pageUtil != null && pageUtil.get("start") != null && pageUtil.getLimit() > 0)
                sql.append(" limit #{start},#{limit}");
            System.out.println("查询sql==" + sql.toString());
            return sql.toString();
        }

        //删除的方法
        public String deleteBatch(Integer[] ids) {
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE tb_blog_tag SET is_deleted=1 WHERE tag_id IN(");
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