package com.grovex.admin.modules.sys.vo;

import lombok.Data;

@Data
public class PageVo {
    /**
     * 分页大小
     */
    private Long size;
    /**
     * 页码
     */
    private Long current;
}
