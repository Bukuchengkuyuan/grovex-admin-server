package com.grovex.admin.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grovex.admin.modules.sys.entity.SysUser;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author ablue
 * @since 2023-12-27
 */
public interface SysUserService extends IService<SysUser> {
    public boolean saveUser(SysUser sysUser);
}
