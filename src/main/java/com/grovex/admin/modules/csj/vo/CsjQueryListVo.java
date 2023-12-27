package com.grovex.admin.modules.csj.vo;

import com.grovex.admin.modules.sys.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CsjQueryListVo extends PageVo {

    @ApiModelProperty("代码位id")
    private String adSlotId;

    @ApiModelProperty("查询日期")
    private String date;
}
