package com.grovex.admin.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *     用户在线表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_online")
@ApiModel(value="Online对象", description="用户在线表")
public class Online implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户 ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer uid;

    @ApiModelProperty(value = "设备 ID")
    private String deviceId;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
