package com.grovex.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grovex.admin.common.utils.MD5Util;
import com.grovex.admin.entity.Online;
import com.grovex.admin.entity.User;
import com.grovex.admin.mapper.OnlineMapper;
import com.grovex.admin.mapper.UserMapper;
import com.grovex.admin.service.OnlineService;
import com.grovex.admin.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 在线用户 服务实现类
 * </p>
 *
 * @author ablue
 * @since 2023-12-27
 */
@Service
public class OnlineServiceImpl extends ServiceImpl<OnlineMapper, Online> implements OnlineService {

    // 注入在线用户表
    @Autowired
    private OnlineMapper onlineMapper;

    public int saveOrUpdate(@Param("uid") int uid, @Param("deviceId") String deviceId){
        return onlineMapper.saveOrUpdate(uid, deviceId);
    }

}
