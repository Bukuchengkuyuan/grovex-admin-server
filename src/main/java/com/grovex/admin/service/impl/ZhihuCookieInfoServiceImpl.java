package com.grovex.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grovex.admin.common.utils.MD5Util;
import com.grovex.admin.entity.User;
import com.grovex.admin.entity.ZhihuCookieInfo;
import com.grovex.admin.mapper.UserMapper;
import com.grovex.admin.mapper.ZhihuCookieInfoMapper;
import com.grovex.admin.service.UserService;
import com.grovex.admin.service.ZhihuCookieInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ablue
 * @since 2023-12-27
 */
@Service
public class ZhihuCookieInfoServiceImpl extends ServiceImpl<ZhihuCookieInfoMapper, ZhihuCookieInfo> implements ZhihuCookieInfoService {

}
