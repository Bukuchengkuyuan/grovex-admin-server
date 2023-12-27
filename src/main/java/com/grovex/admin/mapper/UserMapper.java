package com.grovex.admin.mapper;

import com.grovex.admin.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author ablue
 * @since 2023-12-27
 */
public interface UserMapper extends BaseMapper<User> {

    // 根据用户名和密码查询会员用户
    @Select("SELECT * FROM t_account WHERE username = #{username} and password = #{password} and vip_time > now()")
    User getVipUserByUserNameAndPassword(@Param("username") String username, @Param("password") String password);

}
