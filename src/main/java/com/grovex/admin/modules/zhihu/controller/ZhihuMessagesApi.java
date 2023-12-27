package com.grovex.admin.modules.zhihu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.grovex.admin.common.utils.Result;
import com.grovex.admin.entity.ZhihuCookieInfo;
import com.grovex.admin.modules.zhihu.vo.SendMessageVo;
import com.grovex.admin.service.impl.ZhihuCookieInfoServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 知乎消息接口
 */
@Api(tags = "知乎消息接口")
@RestController
public class ZhihuMessagesApi {


    /**
     * 知乎服务注入
     */
    @Autowired
    private ZhihuCookieInfoServiceImpl zhihuService;


    @ApiOperation("发送消息")
    @PostMapping("/api/sendMessage")
    public Result messages(@RequestBody SendMessageVo vo) {
        QueryWrapper<ZhihuCookieInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", vo.getSendId());
        zhihuService.getOne(queryWrapper);
        return Result.ok();
    }
}
