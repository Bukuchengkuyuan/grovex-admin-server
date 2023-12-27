package com.grovex.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grovex.admin.entity.Online;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

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
public interface OnlineMapper extends BaseMapper<Online> {

    /**
     * 根据uid根deviceId插入或者更新时间
     */
    @Insert("INSERT INTO t_online (uid, device_id, update_time) VALUES (#{uid}, #{deviceId}, now()) ON DUPLICATE KEY UPDATE update_time = now()")
    int saveOrUpdate(@Param("uid") int uid, @Param("deviceId") String deviceId);
}
