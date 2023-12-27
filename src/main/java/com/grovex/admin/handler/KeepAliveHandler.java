package com.grovex.admin.handler;

import com.grovex.admin.entity.User;
import com.grovex.admin.service.impl.OnlineServiceImpl;
import com.grovex.admin.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;


/**
 * 定时任务 从config.txt 中获取用户名和密码 并每分钟发送一次心跳
 */
@Component
@Slf4j
public class KeepAliveHandler {

    // 注入用户服务
    @Autowired
    private UserServiceImpl userService;

    // 注入在线用户服务
    @Autowired
    private OnlineServiceImpl onlineService;

    // 登录用户id
    private int uid;

    // 设备id
    private String deviceId;

    @PostConstruct
    public void init() {
        String filename = "config.txt";
        String account = "";
        String password = "";
        String rootId = "";

        // 从配置文件中获取账号密码根id
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("account")) {
                    int index = line.indexOf('=');
                    account = line.substring(index + 1).trim();
                } else if (line.startsWith("password")) {
                    int index = line.indexOf('=');
                    password = line.substring(index + 1).trim();
                } else if (line.startsWith("root_id")) {
                    int index = line.indexOf('=');
                    rootId = line.substring(index + 1).trim();
                }
            }

            if (account.isEmpty()) {
                throw new IllegalArgumentException("Account is not specified");
            }

            if (password.isEmpty()) {
                throw new IllegalArgumentException("Password is not specified");
            }

            if (!rootId.matches("\\d+")) {
                throw new IllegalArgumentException("Invalid root_id");
            }

            // 打印配置信息
            log.info("当前账号信息为：account:{},password:{},roodId:{}",account,password,rootId);


            User user = userService.getVipUserByUserNameAndPassword(account, password);
            log.info("user is :{}", user);
            uid = user.getId();
            deviceId = rootId;


        } catch (Exception e) {
            log.info("文件读取失败", e);
            System.exit(1);
        }
    }

    /**
     * 启动定时任务 （1min/次）
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void startScheduledTask() {
        log.info("当前时间：{}", System.currentTimeMillis());
        onlineService.saveOrUpdate(uid, deviceId);
    }
}
