package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.BlogTagRelation;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface BlogTagRelationMapper {
    @Delete("DELETE FROM tb_blog_tag_relation WHERE relation_id = #{relationId,jdbcType=BIGINT}")
    int deleteByPrimaryKey(Long relationId);

    @Insert("insert into tb_blog_tag_relation (relation_id, blog_id, tag_id, create_time)" +
            "    values (#{relationId,jdbcType=BIGINT}, #{blogId,jdbcType=BIGINT}, #{tagId,jdbcType=INTEGER}," +
            "      #{createTime,jdbcType=TIMESTAMP})")
    int insert(BlogTagRelation record);

    @InsertProvider(type = BlogTagRelationSqlBuilder.class, method = "insertSelective")
    int insertSelective(BlogTagRelation record);

    @Select("SELECT * FROM tb_blog_tag_relation WHERE relation_id = #{relationId,jdbcType=BIGINT}")
    BlogTagRelation selectByPrimaryKey(Long relationId);

    @Select("SELECT * FROM tb_blog_tag_relation WHERE blog_id = #{blogId,jdbcType=BIGINT} and tag_id = #{tagId,jdbcType=INTEGER} limit 1")
    BlogTagRelation selectByBlogIdAndTagId(@Param("blogId") Long blogId, @Param("tagId") Integer tagId);

    @SelectProvider(type = BlogTagRelationSqlBuilder.class, method = "selectDistinctTagIds")
    List<Long> selectDistinctTagIds(Integer[] tagIds);

    @UpdateProvider(type = BlogTagRelationSqlBuilder.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BlogTagRelation record);

    @Update("UPDATE tb_blog_tag_relation" +
            "    SET blog_id = #{blogId,jdbcType=BIGINT}," +
            "      tag_id = #{tagId,jdbcType=INTEGER}," +
            "      create_time = #{createTime,jdbcType=TIMESTAMP}" +
            "    WHERE relation_id = #{relationId,jdbcType=BIGINT}")
    int updateByPrimaryKey(BlogTagRelation record);

    @InsertProvider(type = BlogTagRelationSqlBuilder.class, method = "batchInsert")
    //加入该注解可以保持对象后，查看对象插入id
    // 批量的时候Param("list") 必须是list，不然id不返回
    @Options(useGeneratedKeys = true, keyProperty = "relationId")
    int batchInsert(@Param("list") List<BlogTagRelation> blogTagRelationList);

    @Delete("DELETE FROM tb_blog_tag_relation WHERE blog_id = #{blogId,jdbcType=BIGINT}")
    int deleteByBlogId(Long blogId);

    class BlogTagRelationSqlBuilder {
        public String insertSelective(BlogTagRelation tagRelation) {
            StringBuilder sql = new StringBuilder();
            StringBuilder sqlValues = new StringBuilder(" values (");
            sql.append("INSERT INTO tb_blog_tag_relation (");
            if (tagRelation.getRelationId() != null) {
                sql.append("relation_id, ");
                sqlValues.append("#{relationId,jdbcType=BIGINT},");
            }
            if (tagRelation.getBlogId() != null) {
                sql.append("blog_id, ");
                sqlValues.append("#{blogId,jdbcType=BIGINT},");
            }
            if (tagRelation.getTagId() != null) {
                sql.append("tag_id, ");
                sqlValues.append("#{tagId,jdbcType=INTEGER},");
            }
            if (tagRelation.getCreateTime() != null) {
                sql.append("create_time) ");
                sqlValues.append("#{createTime,jdbcType=TIMESTAMP} )");
            }
            sql.append(sqlValues);
            System.out.println("sql语句===" + sql.toString());
            return sql.toString();
        }

        public String updateByPrimaryKeySelective(BlogTagRelation tagRelation) {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE tb_blog_tag_relation SET ");
            if (tagRelation.getBlogId() != null) {
                sql.append(" blog_id = #{blogId,jdbcType=BIGINT}, ");
            }
            if (tagRelation.getTagId() != null) {
                sql.append(" tag_id = #{tagId,jdbcType=INTEGER}, ");
            }
            if (tagRelation.getCreateTime() != null) {
                sql.append(" create_time = #{createTime,jdbcType=TIMESTAMP} ");
            }
            sql.append("  WHERE relation_id = #{relationId,jdbcType=BIGINT}");
            return sql.toString();
        }

        public String selectDistinctTagIds(String[] ids) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT DISTINCT(tag_id) FROM tb_blog_tag_relation WHERE tag_id IN (");
            sql.append(StringUtils.join(ids, ",")).append(") ");
            System.out.println("查询sql==" + sql.toString());
            return sql.toString();
        }

        public String batchInsert(Map<String, Object> map) {
            List<BlogTagRelation> blogTagRelationList = (List<BlogTagRelation>) map.get("list");
            StringBuffer sql = new StringBuffer();
            sql.append(" INSERT INTO tb_blog_tag_relation(blog_id, tag_id)");
            sql.append(" VALUES ");
            int length = blogTagRelationList.size();
            MessageFormat mf = new MessageFormat("(#'{'list[{0}].blogId,jdbcType=BIGINT},#'{'list[{0}].tagId,jdbcType=INTEGER})");
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