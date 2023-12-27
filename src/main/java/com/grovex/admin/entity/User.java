package com.grovex.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.grovex.admin.common.validator.group.AddGroup;
import com.grovex.admin.common.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author ablue
 * @since 2023-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_account")
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @ApiModelProperty(value = "删除判断")
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    @ApiModelProperty(value = "是否加入了黑名单 0.不是 1.是的")
    private Integer blacklist;

    @ApiModelProperty(value = "卡密绑定id")
    private String cardId;

    @ApiModelProperty(value = "用户电脑的机器码")
    private String code;

    @ApiModelProperty(value = "用户注册的时候ip地址")
    private String createIp;

    @ApiModelProperty(value = "创建时候的ip信息")
    private String createIpInfo;

    @ApiModelProperty(value = "用户的真实姓名")
    private String name;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户的联系手机号")
    private String phoneNumber;

    @ApiModelProperty(value = "用户的联系QQ")
    private String qq;

    @ApiModelProperty(value = "安全码，找回密码用")
    private String securityCode;

    @ApiModelProperty(value = "软件绑定id")
    private String softId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "vip时间")
    @TableField(fill = FieldFill.INSERT)
    private Date vipTime;
}
