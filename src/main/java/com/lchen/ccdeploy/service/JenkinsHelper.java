package com.lchen.ccdeploy.service;

import com.lchen.ccdeploy.config.handler.GlobalSession;
import com.lchen.ccdeploy.model.JenkinsBuildHistory;
import com.lchen.ccdeploy.model.constants.JenkinsBuildStatus;
import com.lchen.ccdeploy.utils.JenkinsClient;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.lchen.ccdeploy.model.JenkinsBuildHistory.buildHistory;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@Slf4j
@Component
public class JenkinsHelper {

    @Autowired
    private JenkinsClient jenkinsClient;
    @Autowired
    private JenkinsService jenkinsService;
    @Autowired
    private DeployBuildThread deployBuildThread;

    @Scheduled(fixedRate = 6 * 1000)
    private void run() {
        Set<String> contexts = GlobalSession.getContextSessions().keySet();
        contexts.forEach(this::updateJenkinsByContext);
    }

    public void updateJenkinsByContext(String context) {
        try {
            Optional<Long> optionalVersion = jenkinsClient.queueing(context);
            optionalVersion.ifPresent(version -> updateJenkinsBuildQueueing(context, version));
            List<Build> builds = jenkinsClient.buildsByJob(context);
            for (Build build : builds) {
                updateJenkinsBuild(build, context);
            }
        } catch (IOException e) {
            log.error("获取应用{}的构建列表失败", context, e);
        }
    }

    private void updateJenkinsBuildQueueing(String context, Long version) {
        JenkinsBuildHistory jenkinsBuildHistory = JenkinsBuildHistory.builder()
                .jobName(context)
                .version(version.intValue())
                .buildStatus(JenkinsBuildStatus.QUEUE)
                .build();
        jenkinsService.createOrUpdate(jenkinsBuildHistory);
    }

    private void updateJenkinsBuild(Build build, String context) {
        try {
            BuildWithDetails details = build.details();
            JenkinsBuildHistory jenkinsBuildHistory = buildHistory(details, context);
            jenkinsService.createOrUpdate(jenkinsBuildHistory);
        } catch (IOException e) {
        }
    }

    public void startBuild(@NonNull String context) {
        try {
            jenkinsClient.build(context);
            //更新数据库
            updateJenkinsByContext(context);
            //推送
            deployBuildThread.pushByContext(context);
        } catch (IOException e) {
            log.error("立即构建失败;context={},{}", context, e);
        }
    }


}
