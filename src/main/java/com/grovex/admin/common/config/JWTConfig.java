package com.grovex.admin.common.config;

import com.grovex.admin.common.utils.GsonUtil;
import com.grovex.admin.common.utils.JwtUtil;
import com.grovex.admin.common.utils.RedisUtil;
import com.grovex.admin.common.utils.Result;
import com.grovex.admin.modules.sys.vo.JwtVo;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


/**
 * JWT 拦截器配置
 *
 * @auther: ablue
 * @date: 2024/01/03
 */
@Slf4j
@Configuration
public class JWTConfig {

    @Value("${spring.appname}")
    private String appName;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${jwt.expire}")
    private long EXPIRE;

    @Bean(name = "JWTInterceptor")
    public WebMvcConfigurer JWTInterceptor() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new JWTInterceptor())
                        .addPathPatterns("/**")
                        .excludePathPatterns("/sys/sys-user/login/*", "/sys/sys-user/register", "/sys/sys-user/updateToken")
                        .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
            }
        };
    }

    /**
     * JWT 拦截器
     * @auther: ablue
     * @date: 2024/01/04
     */
    class JWTInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String token = request.getHeader("Authorization");
            if (StringUtils.isNotEmpty(token) && token.startsWith("Bearer ")) {
                token = token.substring(7);
                try {
                    Claims claims = JwtUtil.getTokenBody(token);
                    Long expire = claims.getExpiration().getTime();
                    Long currentTime = new Date().getTime();
                    if (currentTime <= expire || redisUtil.hasKey(appName + GsonUtil.fromJson(String.valueOf(claims.get("data")), JwtVo.class).getId())) {
                        // 如果 token 未过期或 Redis 中存在 token，则继续
                        if (currentTime > expire) {
                            // Token 过期但 Redis 中存在，刷新 token
                            refreshToken(claims, response);
                        }
                        return true;
                    }
                } catch (Exception e) {
                    log.error("Token 验证失败", e);
                }
            }
            returnJsonData(response);
            return false;
        }
    }

    /**
     * 返回 json 数据
     * @param response
     * @throws IOException
     */
    public void returnJsonData(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.print(GsonUtil.toJson(Result.error().message("token 失效或过期").code(HttpStatus.SC_UNAUTHORIZED)));
    }

    /**
     * 刷新 token
     * @param claims token 中的数据
     * @param response 响应
     */
    public void refreshToken(Claims claims, HttpServletResponse response) {
        long redisExpire = redisUtil.getExpire(appName + GsonUtil.fromJson(String.valueOf(claims.get("data")), JwtVo.class).getId());

        // 30秒内刷新过 token，不再刷新
        if (redisExpire > (new Date().getTime() - EXPIRE) / 1000 ) {
            JwtVo jwt = GsonUtil.fromJson(String.valueOf(claims.get("data")), JwtVo.class);
            String newToken = JwtUtil.getJwtToken(jwt, EXPIRE);
            redisUtil.set(appName + jwt.getId(), newToken, EXPIRE / 1000 * 2);
            response.setHeader("tk", newToken);
        }
    }
}
