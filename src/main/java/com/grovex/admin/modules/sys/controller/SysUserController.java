package com.grovex.admin.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.grovex.admin.common.utils.*;
import com.grovex.admin.common.validator.group.sys.LoginGroup;
import com.grovex.admin.common.validator.group.sys.RegisterGroup;
import com.grovex.admin.modules.sys.entity.SysRole;
import com.grovex.admin.modules.sys.entity.SysUser;
import com.grovex.admin.modules.sys.entity.SysUserRole;
import com.grovex.admin.modules.sys.service.SysRoleService;
import com.grovex.admin.modules.sys.service.SysUserRoleService;
import com.grovex.admin.modules.sys.service.SysUserService;
import com.grovex.admin.modules.sys.vo.JwtVo;
import com.grovex.admin.modules.sys.vo.NamePwdLoginVo;
import com.grovex.admin.modules.sys.vo.PhonePwdLoginVo;
import com.grovex.admin.modules.sys.vo.RegisterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author ablue
 * @since 2023-12-27
 */
@RestController
@RequestMapping("/sys/sys-user")
@Api(tags = "用户登录、注册操作")
public class SysUserController {


    @Value("${spring.appname}")
    private String appName;

    /**
     * 用于操作 sys_user 表
     */
    @Autowired
    private SysUserService sysUserService;
    /**
     * 用于操作 sys_user_role 表
     */
    @Autowired
    private SysUserRoleService sysUserRoleService;
    /**
     * 用于操作 sys_user_role 表
     */
    @Autowired
    private SysRoleService sysRoleService;
    /**
     * 用于操作 redis
     */
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 常量，表示用户密码登录操作
     */
    private static final String USER_NAME_STATUS = "0";
    /**
     * 常量，表示手机号密码登录操作
     */
    private static final String PHONE_STATUS = "1";

    /**
     * 获取用户角色
     * @param sysUser
     * @return
     */
    private String getUserRole(SysUser sysUser){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", sysUser.getId());
        SysUserRole sysUserRole = sysUserRoleService.getOne(queryWrapper);
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("id", sysUserRole.getRoleId());
        SysRole role = sysRoleService.getOne(queryWrapper1);
        return role.getRoleName();
    }

    /**
     * 获取 jwt
     * @return jwt
     */
    private String getJwt(SysUser sysUser) {
        // 获取需要保存在 jwt 中的数据
        JwtVo jwtVo = new JwtVo();
        jwtVo.setId(sysUser.getId());
        jwtVo.setName(sysUser.getName());
        jwtVo.setRoleType(getUserRole(sysUser));
        jwtVo.setTime(new Date().getTime());
        // 获取 jwt 数据，设置过期时间为 3h
        String jwt = JwtUtil.getJwtToken(jwtVo, 1000L * 60 * 60 * 3);
        // 判断用户是否重复登录（code 有值则重复登录，需要保留最新的登录者，剔除前一个登录者）
        String code = redisUtil.get(String.valueOf(sysUser.getId()));
        // 获取当前时间戳
        Long currentTime = new Date().getTime();
        // 如果 redis 中存在 jwt 数据，则根据时间戳比较谁为最新的登陆者
        if (StringUtils.isNotEmpty(code)) {
            // 获取 redis 中存储的 jwt 数据
            JwtVo redisJwt = GsonUtil.fromJson(String.valueOf(JwtUtil.getTokenBody(code).get("data")), JwtVo.class);
            // redis jwt 大于 当前时间戳，则 redis 中 jwt 为最新登录者，当前登录失败
            if (redisJwt.getTime() > currentTime) {
                return null;
            }
        }
        // 把数据存放在 redis 中，设置过期时间为 3 * 60 分钟
        redisUtil.set(String.valueOf(sysUser.getId()), jwt, 60 * 60 * 3);
        return jwt;
    }

    /**
     * 使用密码进行真实登录操作
     * @param account 账号（用户名或手机号）
     * @param pwd 密码
     * @param status 是否使用用户名登录（0 表示用户名登录，1 表示手机号登录）
     * @return jwt
     */
    private String pwdLogin(String account, String pwd, String status) {
        // 新增查询条件
        QueryWrapper queryWrapper = new QueryWrapper();
        // 如果是用户名 + 密码登录，则根据 姓名 + 密码 查找数据
        if (USER_NAME_STATUS.equals(status)) {
            queryWrapper.eq("name", account);
        }
        // 如果是手机号 + 密码登录，则根据 手机号 + 密码 查找数据
        if (PHONE_STATUS.equals(status)) {
            queryWrapper.eq("mobile", account);
        }
        // 添加密码条件，密码进行 MD5 加密后再与数据库数据比较
        queryWrapper.eq("password", MD5Util.encrypt(pwd));
        // 获取用户数据
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        // 如果存在用户数据
        if (sysUser != null) {
            return getJwt(sysUser);
        }
        return null;
    }

    @ApiOperation(value = "使用用户名、密码登录")
    @PostMapping("/login/namePwdLogin")
    public Result namePwdLogin(@Validated({LoginGroup.class}) @RequestBody NamePwdLoginVo namePwdLoginVo) {
        JwtVo jwtVo = new JwtVo();
        jwtVo.setId(1L);
        jwtVo.setName(namePwdLoginVo.getUserName());
        String token = JwtUtil.getJwtToken(jwtVo); // 假
        redisUtil.set(appName + 1, token, 60);
        if (StringUtils.isNotEmpty(token)) {
            return Result.ok().message("登录成功").data("token", token);
        }
        return Result.error().message("登录失败").code(HttpStatus.SC_UNAUTHORIZED);
    }

    @ApiOperation(value = "使用手机号、密码登录")
    @PostMapping("/login/phonePwdLogin")
    public Result phonePwdLogin(@Validated({LoginGroup.class}) @RequestBody PhonePwdLoginVo phonePwdLoginVo) {
        String jwt = pwdLogin(phonePwdLoginVo.getPhone(), phonePwdLoginVo.getPassword(), PHONE_STATUS);
        if (StringUtils.isNotEmpty(jwt)) {
            return Result.ok().message("登录成功").data("token", jwt);
        }
        return Result.error().message("登录失败").code(HttpStatus.SC_UNAUTHORIZED);
    }

    @ApiOperation(value = "用户登出")
    @GetMapping("/logout")
    public Result logout(@SessionAttribute("jwt")JwtVo jwtvo) {
        // 用户存在时
        if (jwtvo != null && jwtvo.getId() != null) {
            // 生成并返回一个无效的 token
            String jwt = JwtUtil.getJwtToken(null, 1000L);
            // 删除 redis 中的 token
            redisUtil.del(String.valueOf(jwtvo.getId()));
            return Result.ok().message("登出成功").data("token", jwt);
        }
        return Result.error().message("登出失败");
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result register(@Validated({RegisterGroup.class}) @RequestBody RegisterVo registerVo) {
        if (save(registerVo)) {
            return Result.ok().message("用户注册成功");
        }
        return Result.error().message("用户注册失败").code(HttpStatus.SC_UNAUTHORIZED);
    }

    /**
     * 真实注册操作
     * @param registerVo 注册数据
     * @return true 为插入成功， false 为失败
     */
    public boolean save(RegisterVo registerVo) {
        // 判断 redis 中是否存在 验证码
        String code = redisUtil.get(registerVo.getPhone());
        // redis 中存在验证码且与当前验证码相同
        SysUser sysUser = new SysUser();
        sysUser.setName(registerVo.getUserName()).setPassword(MD5Util.encrypt(registerVo.getPassword()));
        sysUser.setMobile(registerVo.getPhone());
        return sysUserService.saveUser(sysUser);
    }

    @ApiOperation(value = "刷新 token")
    @GetMapping("/updateToken")
    public Result updateToken(HttpRequest request) {
        request.getHeaders("");
        return Result.ok();
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/getUserInfo")
    public Result getUserInfo(HttpServletRequest request) {

        return Result.ok()
                .data("userId",JwtUtil.getTokenBody(request));
    }
}

