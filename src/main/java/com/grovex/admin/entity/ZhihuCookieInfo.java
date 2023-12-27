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

/**
 * <p>
 *     知乎Cookie表
 * </p>
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("zhihu_cookie_info")
@ApiModel(value="ZhihuCookieInfo对象", description="知乎Cookie表")
public class ZhihuCookieInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty(value = "url_token")
    private String urlToken;

    @ApiModelProperty(value = "status")
    private String status;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "uid")
    private String uid;

    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "banReason")
    private String banReason;

    @ApiModelProperty(value = "cookie")
    private String cookie;

    @ApiModelProperty(value = "del_flag")
    private String delFlag;

    @ApiModelProperty(value = "avatar_url")
    private String avatarUrl;

    @ApiModelProperty(value = "remark")
    private String remark;

}
