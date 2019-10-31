package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.BlogTag;
import com.site.blog.my.core.entity.BlogTagCount;
import com.site.blog.my.core.util.PageQueryUtil;
import org.apache.commons.lang3.StringUtils;
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

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @InsertProvider(type = BlogTagSqlBuilder.class, method = "batchInsertBlogTag")
    //加入该注解可以保持对象后，查看对象插入id
    @Options(useGeneratedKeys = true, keyProperty = "tagId")
    int batchInsertBlogTag(@Param("list") List<BlogTag> tagList);

    class BlogTagSqlBuilder extends SQL {

        private static final String TABLE_NAME = "tb_blog_tag";

        public String insertSelective(BlogTag blog) {
            String sql = new SQL() {{
                INSERT_INTO(TABLE_NAME);
                if (blog.getTagId() != null) {
                    VALUES("tag_id", "#{tagId,jdbcType=INTEGER}");
                }
                if (StringUtils.isNotBlank(blog.getTagName())) {
                    VALUES("tag_name", "#{tagName,jdbcType=VARCHAR}");
                }
                if (blog.getIsDeleted() != null) {
                    VALUES("is_deleted", "#{is_deleted,jdbcType=TINYINT}");
                }
                if (blog.getCreateTime() == null) {
                    blog.setCreateTime(new Date());
                }
                VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
            }}.toString();
            System.out.println("sql语句===" + sql);
            return sql;
        }

        public String updateByPrimaryKeySelective(BlogTag blog) {
            String sql = new SQL() {{
                UPDATE(TABLE_NAME);
                if (StringUtils.isNotBlank(blog.getTagName())) {
                    SET("tag_name = #{tagName,jdbcType=VARCHAR}");
                }
                if (blog.getIsDeleted() != null) {
                    SET("is_deleted = #{isDeleted,jdbcType=TINYINT}");
                }
                WHERE("tag_id = #{tagId,jdbcType=INTEGER}");
            }}.toString();
            System.out.println("sql语句===" + sql);
            return sql;
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

        public String batchInsertBlogTag(Map<String, Object> map) {
            //由Mapper传入的List在SQL构造类中将会包装在一个Map里，所以这里的参数是Map
            //key就是Mapper中@Param注解配置的名称
            List<BlogTag> tagList = (List<BlogTag>) map.get("list");
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO tb_blog_tag(tag_name)");
            sql.append(" VALUES ");
            int length = tagList.size();
            MessageFormat mf = new MessageFormat("(#'{'list[{0}].tagName})");
            for (int i = 0; i < length; i++) {
                sql.append(mf.format(new Object[]{i}));
                if (i < length - 1) {
                    sql.append(",");
                }
            }
            return sql.toString();
        }
    }
}