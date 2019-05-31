package com.lchen.ccdeploy.service;

import com.lchen.ccdeploy.config.handler.GlobalSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Set;

@Component
public class DeployBuildThread {

    @Autowired
    private JenkinsService jenkinsService;

    @Scheduled(fixedRate = 10 * 1000)
    public void pushAll() {
        Set<String> contexts = GlobalSession.getContextSessions().keySet();
        contexts.forEach(this::pushByContext);
    }

    /**
     * 为每个应用推送
     */
    public void pushByContext(String context) {
        //获取构建信息

        //获取部署信息
    }

    /**
     * 理论上一个用户能打开无数个websession，然后为每个session推送相关的内容
     *
     * @param session
     */
    public void pushBySession(WebSocketSession session) {
    }
}
