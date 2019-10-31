package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.BlogConfig;
import com.site.blog.my.core.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;


/**
 * 使用myBatis的通用插件tk.mybatis
 */
@Component
@Mapper
public interface BlogConfigMapper extends MyMapper<BlogConfig> {

    @Select("select config_name, config_value, create_time, update_time from blog_config where config_name =  #{configName,jdbcType=VARCHAR} ")
    BlogConfig findByConfigName(String configName);

}