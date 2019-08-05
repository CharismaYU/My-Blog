package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.AdminUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AdminUserMapper {
    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    /**
     * 登陆方法
     *
     * @param userName
     * @param password
     * @return
     */
    @Select("select admin_user_id, login_user_name, login_password, login_plaintext_password, nick_name, locked from tb_admin_user where login_user_name = #{userName} AND login_password = #{password} AND locked = 0")
    AdminUser login(@Param("userName") String userName, @Param("password") String password);

    @Select("select * from tb_admin_user where admin_user_id = #{adminUserId}")
    AdminUser selectByPrimaryKey(Integer adminUserId);

    @Select("select * from tb_admin_user ")
    List<AdminUser> findAll();

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);


}