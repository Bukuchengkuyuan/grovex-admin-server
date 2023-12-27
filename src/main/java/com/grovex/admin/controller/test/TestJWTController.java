package com.grovex.admin.controller.test;

import com.grovex.admin.common.utils.JwtUtil;
import com.grovex.admin.common.utils.Result;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test/jwt")
@RestController
@Api(tags = "测试 JWT")
public class TestJWTController {

    @ApiOperation(value = "获取 token")
    @PostMapping("/getToken")
    public Result testJwt() {
        return Result.ok().data("token", JwtUtil.getJwtToken("tom"));
    }

    @ApiOperation(value = "测试是否过期")
    @PostMapping("/testExpire")
    public Result testJwtExpire(String jwtToken) {
        if (JwtUtil.checkToken(jwtToken)) {
            Claims claims = JwtUtil.getTokenBody(jwtToken);
            return Result.ok().message("token 未过期").data("claims", claims);
        }
        return Result.ok().message("token 已过期");
    }
}
