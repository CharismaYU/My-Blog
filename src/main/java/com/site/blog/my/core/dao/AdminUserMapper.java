package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.AdminUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface AdminUserMapper {
    @Insert("insert into tb_admin_user (admin_user_id, login_user_name, login_password, " +
            "      nick_name, locked)" +
            "    values (#{adminUserId,jdbcType=INTEGER}, #{loginUserName,jdbcType=VARCHAR}, #{loginPassword,jdbcType=VARCHAR}, " +
            "      #{nickName,jdbcType=VARCHAR}, #{locked,jdbcType=TINYINT})")
    int insert(AdminUser record);

    @InsertProvider(type = AdminUserSqlBuilder.class, method = "insertSelective")
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

    @UpdateProvider(type = AdminUserSqlBuilder.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AdminUser user);

    @Update(" update tb_admin_user" +
            "    set login_user_name = #{loginUserName,jdbcType=VARCHAR}," +
            "      login_password = #{loginPassword,jdbcType=VARCHAR}," +
            "      login_plaintext_password = #{plaintextPassword,jdbcType=VARCHAR}," +
            "      nick_name = #{nickName,jdbcType=VARCHAR}," +
            "      locked = #{locked,jdbcType=TINYINT}" +
            "    where admin_user_id = #{adminUserId,jdbcType=INTEGER}")
    int updateByPrimaryKey(AdminUser record);

    class AdminUserSqlBuilder extends SQL {
        private static final String TABLE_NAME = "tb_admin_user";

        public String insertSelective(AdminUser user) {
            String sql = new SQL() {{
                INSERT_INTO(TABLE_NAME);
                if (user.getAdminUserId() != null) {
                    VALUES("admin_user_id", "#{adminUserId,jdbcType=INTEGER}");
                }
                if (StringUtils.isNotBlank(user.getLoginUserName())) {
                    VALUES("login_user_name", "#{loginUserName,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(user.getLoginPassword())) {
                    VALUES("login_password", "#{loginPassword,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(user.getPlaintextPassword())) {
                    VALUES("login_plaintext_password", "#{plaintextPassword,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(user.getNickName())) {
                    VALUES("nick_name", "#{nickName,jdbcType=VARCHAR}");
                }
                if (user.getLocked() != null) {
                    VALUES("locked", "#{locked,jdbcType=TINYINT}");
                }
            }}.toString();
            System.out.println("sql语句===" + sql);
            return sql;
        }

        public String updateByPrimaryKeySelective(AdminUser user) {
            return new SQL() {{
                UPDATE(TABLE_NAME);
                if (StringUtils.isNotBlank(user.getLoginUserName())) {
                    SET("login_user_name = #{loginUserName,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(user.getLoginPassword())) {
                    SET("login_password = #{loginPassword,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(user.getPlaintextPassword())) {
                    SET("login_plaintext_password = #{plaintextPassword,jdbcType=VARCHAR}");
                }
                if (StringUtils.isNotBlank(user.getNickName())) {
                    SET("nick_name = #{nickName,jdbcType=VARCHAR}");
                }
                if (user.getLocked() != null) {
                    SET("locked = #{locked,jdbcType=TINYINT}");
                }
                WHERE("admin_user_id = #{adminUserId,jdbcType=INTEGER}");
            }}.toString();
        }
    }

}