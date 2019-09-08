package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.BlogConfig;
import com.site.blog.my.core.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.util.StringUtil;

@Component
@Mapper
public interface BlogConfigMapper extends MyMapper<BlogConfig> {

    /*@Select("select config_name, config_value, create_time, update_time from blog_config ")
    List<BlogConfig> selectAll();*/

    @UpdateProvider(type = BlogConfigSqlBuilder.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BlogConfig record);

    @Select("select config_name, config_value, create_time, update_time from blog_config where config_name =  #{configName,jdbcType=VARCHAR} ")
    BlogConfig findByConfigName(String configName);

    class BlogConfigSqlBuilder {
        public String updateByPrimaryKeySelective(BlogConfig blogConfig) {
            StringBuilder sql = new StringBuilder();
            sql.append("update blog_config set ");
            if (StringUtil.isNotEmpty(blogConfig.getConfigValue())) {
                sql.append(" config_value = #{configValue,jdbcType=VARCHAR}, ");
            }
            if (blogConfig.getCreateTime() != null) {
                sql.append(" create_time = #{createTime,jdbcType=TIMESTAMP}, ");
            }
            if (blogConfig.getUpdateTime() != null) {
                sql.append(" update_time = #{updateTime,jdbcType=TIMESTAMP}");
            }
            sql.append("  where config_name = #{configName,jdbcType=VARCHAR}");
            return sql.toString();
        }
    }
}