package com.grovex.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grovex.admin.entity.ZhihuCookieInfo;

/**
 *
 * CREATE TABLE t_online (
 *                           uid int NOT NULL COMMENT '用户 ID',
 *                           device_id varchar(8) NOT NULL COMMENT '设备 ID',
 *                           update_time datetime DEFAULT NULL COMMENT '更新时间',
 *                           PRIMARY KEY(uid, device_id),
 *                           UNIQUE INDEX(uid, device_id)
 * ) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='在线用户表';
 */
public interface ZhihuCookieInfoMapper extends BaseMapper<ZhihuCookieInfo> {

}
