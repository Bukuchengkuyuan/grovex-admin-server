package com.grovex.admin.modules.csj.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Data Transfer Object (DTO) representing Ad Data.
 */
@Data
public class AdDataDto {
    private float ad_fill_rate;           // 物料填充率=物料返回量/物料请求量
    private float ad_impression_rate;  // 物料展示率=展示量/物料返回量
    private int ad_request;             // 物料请求量
    private long ad_slot_id;            // 代码位id
    private int ad_slot_type;           // 1（信息流）、2（Banner）、3（开屏）、4（插屏）、5（激励视频）、6（全屏视频）、7（Draw信息流）、8（贴片）、9（新插屏广告）
    private int ad_slot_type_v2;        // 1（信息流）、2（Banner）、3（开屏）、4（插屏）、5（激励视频）、6（全屏视频）、7（Draw信息流）、8（贴片）、91（新插屏全屏）、92（新插屏半屏）
    private int app_id;                 // 应用id
    private String app_name;            // 应用名称
    private int bidding_type;           // 竞价类型. 0 (标准) 、1 (客户端竞价) 、2 (服务端竞价)
    private int click;                  // 点击量
    private float click_rate;          // 点击率
    private String code_name;           // 描述
    private String currency;            // 币种单位
    private String date;                // 日期
    private double ecpm;                // 预估ecpm
    private float fill_rate;              // 填充率
    private int media_m_ssr;            // 媒体m_ssr
    private String media_name;          // 媒体名称
    private String os;                  // 操作系统
    private String package_name;        // 包名
    private String region;              // 国家或地区代码，同 ISO3166-1 标准，使用两位字母编码
    private int request;                // 广告请求量
    private int response;               // 广告返回量
    @JsonProperty("return")
    private int returnCount; // 物料返回
    private float revenue;             // 预估收益
    private int show;                   // 展示量
    private int time_zone;              // 时区
    private int use_mediation;          // 是否使用穿山甲聚合. 0 (否) 、1 (是)
    private int win_rate;               // 胜率

}
