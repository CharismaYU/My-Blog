package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.AdminUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.util.StringUtil;

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

    class AdminUserSqlBuilder {
        public String insertSelective(AdminUser user) {
            StringBuilder sql = new StringBuilder();
            StringBuilder sqlValues = new StringBuilder(" values (");
            sql.append("insert into tb_admin_user (");
            if (user.getAdminUserId() != null) {
                sql.append("admin_user_id, ");
                sqlValues.append("#{adminUserId,jdbcType=INTEGER},");
            }
            if (user.getLoginUserName() != null) {
                sql.append("login_user_name, ");
                sqlValues.append("#{loginUserName,jdbcType=VARCHAR},");
            }
            if (user.getLoginPassword() != null) {
                sql.append("login_password, ");
                sqlValues.append("#{loginPassword,jdbcType=VARCHAR},");
            }
            if (user.getPlaintextPassword() != null) {
                sql.append("login_plaintext_password, ");
                sqlValues.append("#{plaintextPassword,jdbcType=VARCHAR},");
            }
            if (user.getNickName() != null) {
                sql.append("nick_name, ");
                sqlValues.append("#{nickName,jdbcType=VARCHAR},");
            }
            if (user.getLocked() != null) {
                sql.append("locked) ");
                sqlValues.append("#{locked,jdbcType=TINYINT} )");
            }
            sql.append(sqlValues);
            System.out.println("sql语句===" + sql.toString());
            return sql.toString();
        }

        public String updateByPrimaryKeySelective(AdminUser user) {
            StringBuilder sql = new StringBuilder();
            sql.append("update blog_config set ");
            if (StringUtil.isNotEmpty(user.getLoginUserName())) {
                sql.append(" login_user_name = #{loginUserName,jdbcType=VARCHAR}, ");
            }
            if (StringUtil.isNotEmpty(user.getLoginPassword())) {
                sql.append(" login_password = #{loginPassword,jdbcType=VARCHAR}, ");
            }
            if (StringUtil.isNotEmpty(user.getPlaintextPassword())) {
                sql.append(" login_plaintext_password = #{plaintextPassword,jdbcType=VARCHAR}, ");
            }
            if (StringUtil.isNotEmpty(user.getNickName())) {
                sql.append(" nick_name = #{nickName,jdbcType=VARCHAR}, ");
            }
            if (user.getLocked() != null) {
                sql.append(" locked = #{locked,jdbcType=TINYINT}");
            }
            sql.append("  where admin_user_id = #{adminUserId,jdbcType=INTEGER}");
            return sql.toString();
        }
    }

}