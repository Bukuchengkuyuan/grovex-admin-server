package com.grovex.admin.modules.csj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "CsjData对象", description = "穿山甲数据")
public class CsjData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "时区")
    private String timeZone;

    @ApiModelProperty(value = "币种单位")
    private String currency;

    @ApiModelProperty(value = "国家或地区代码，同 ISO3166-1 标准，使用两位字母编码")
    private String region;

    @ApiModelProperty(value = "应用id")
    private Integer appId;

    @ApiModelProperty(value = "应用名称")
    private String appName;

    @TableId(value = "ad_slot_id", type = IdType.INPUT)
    @ApiModelProperty(value = "代码位id")
    private Integer adSlotId;

    @ApiModelProperty(value = "1（信息流）、2（Banner）、3（开屏）、4（插屏）、5（激励视频）、6（全屏视频）、7（Draw信息流）、8（贴片）、9（新插屏广告）")
    private Integer adSlotType;

    @ApiModelProperty(value = "1（信息流）、2（Banner）、3（开屏）、4（插屏）、5（激励视频）、6（全屏视频）、7（Draw信息流）、8（贴片）、91（新插屏全屏）、92（新插屏半屏）")
    private Integer adSlotTypeV2;

    @ApiModelProperty(value = "广告请求量")
    private Integer request;

    @ApiModelProperty(value = "物料返回")
    private Integer returnCount;

    @ApiModelProperty(value = "填充率")
    private Float fillRate;

    @ApiModelProperty(value = "展示量")
    private Integer showCount; // Modified column name from 'show' to 'showCount'

    @ApiModelProperty(value = "点击量")
    private Integer click;

    @ApiModelProperty(value = "点击率")
    private Float clickRate;

    @ApiModelProperty(value = "预估收益（T日）真实收益(T-1日10点后-国内；T-1日14点以后-海外）")
    private Float revenue;

    @ApiModelProperty(value = "预估ecpm")
    private Double ecpm;

    @ApiModelProperty(value = "包名")
    private String packageName;

    @ApiModelProperty(value = "是否使用穿山甲聚合. 0 (否) 、1 (是)")
    private Integer useMediation;

    @ApiModelProperty(value = "竞价类型. 0 (标准) 、1 (客户端竞价) 、2 (服务端竞价)")
    private Integer biddingType;

    @ApiModelProperty(value = "物料请求量")
    private Integer adRequest;

    @ApiModelProperty(value = "广告返回量")
    private Integer response;

    @ApiModelProperty(value = "物料填充率=物料返回量/物料请求量")
    private Float adFillRate;

    @ApiModelProperty(value = "物料展示率=展示量/物料返回量")
    private Float adImpressionRate;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "日期")
    private String date;

    @ApiModelProperty(value = "系统")
    private String os;

}
