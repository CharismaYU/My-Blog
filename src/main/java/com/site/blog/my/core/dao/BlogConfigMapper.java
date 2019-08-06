package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.BlogConfig;
import com.site.blog.my.core.util.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BlogConfigMapper extends MyMapper<BlogConfig> {

    /*@Select("select config_name, config_value, create_time, update_time from blog_config ")
    List<BlogConfig> selectAll();*/

    int updateByPrimaryKeySelective(BlogConfig record);

    @Select("select config_name, config_value, create_time, update_time from blog_config where config_name =  #{configName,jdbcType=VARCHAR} ")
    BlogConfig findByConfigName(String configName);
}