package com.lchen.ccdeploy.thread;

import com.lchen.ccdeploy.config.handler.GlobalSession;
import com.lchen.ccdeploy.model.DeploymentBuild;
import com.lchen.ccdeploy.model.DeploymentResult;
import com.lchen.ccdeploy.model.JenkinsBuild;
import com.lchen.ccdeploy.model.k8s.K8sPod;
import com.lchen.ccdeploy.service.DeploymentService;
import com.lchen.ccdeploy.service.JenkinsService;
import com.lchen.ccdeploy.service.KubernetesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class DeployBuildThread {

    @Autowired
    private JenkinsService jenkinsService;
    @Autowired
    private DeploymentService deploymentService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private KubernetesService kubernetesService;

    @Scheduled(fixedRate = 3 * 1000)
    public void pushAll() {
        Set<String> contexts = GlobalSession.getContextSessions().keySet();
        contexts.forEach(this::pushByContext);
    }

    @Scheduled(fixedRate = 20 * 1000)
    public void pushPod() {
        Set<String> contexts = GlobalSession.getContextSessions().keySet();
        contexts.forEach(this::pushPodByContext);
    }

    /**
     * 为每个应用推送
     */
    public void pushByContext(String context) {
        //获取构建信息
        List<JenkinsBuild> jenkinsBuilds = jenkinsService.listJenkinsBuilds(context);
        //获取部署信息
        List<DeploymentResult> deploymentResults = deploymentService.listDeploymentResult(context);
        DeploymentBuild deploymentBuild = DeploymentBuild.builder()
                .deploymentHistories(deploymentResults)
                .jenkinsBuilds(jenkinsBuilds)
                .build();
        List<WebSocketSession> webSocketSessions = GlobalSession.getContextSessions().get(context);
        webSocketSessions.forEach(session -> pushBySession(session, deploymentBuild));
    }

    /**
     * 为每个应用推送pod相关信息
     *
     * @param context
     */
    public void pushPodByContext(String context) {
        int podNumber = kubernetesService.getReplicas(context);
        K8sPod k8sPod = K8sPod.builder().replicas(podNumber).build();
        GlobalSession.getContextSessions().get(context).forEach(session -> pushPodBySession(session, k8sPod));
    }

    /**
     * 理论上一个用户能打开无数个websession，然后为每个session推送相关的内容
     *
     * @param session
     */
    public void pushBySession(WebSocketSession session, DeploymentBuild deploymentBuild) {
        try {
            GlobalSession.sendMessage(session, deploymentBuild.toMessage());
        } catch (IOException e) {
            log.error("推送构建部署信息失败", e);
        }
    }

    public void pushPodBySession(WebSocketSession session, K8sPod k8sPod) {
        try {
            GlobalSession.sendMessage(session, k8sPod.toMessage());
        } catch (IOException e) {
            log.error("推送应用k8s信息失败", e);
        }
    }

    @PreDestroy
    public void destroy() {
        System.out.println("bean销毁......");
        ThreadPoolTaskScheduler scheduler = (ThreadPoolTaskScheduler) applicationContext.getBean("scheduler");
        scheduler.shutdown();
    }
}
