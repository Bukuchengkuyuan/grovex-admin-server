package com.grovex.admin.modules.zhihu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 发送消息VO
 */
@Data
@ApiModel(value = "发送消息VO")
public class SendMessageVo {
    @ApiModelProperty(value = "发送者id")
    private String sendId;

    @ApiModelProperty(value = "接收者id")
    private String receiveId;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "消息类型")
    private String type;

    @ApiModelProperty(value = "用户id")
    private String uid;
}
