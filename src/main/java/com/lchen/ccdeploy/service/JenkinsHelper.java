package com.lchen.ccdeploy.service;

import com.lchen.ccdeploy.config.handler.GlobalSession;
import com.lchen.ccdeploy.model.JenkinsBuildHistory;
import com.lchen.ccdeploy.utils.JenkinsClient;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@Component
public class JenkinsHelper {

    @Autowired
    private JenkinsClient jenkinsClient;
    @Autowired
    private JenkinsService jenkinsService;

    @Scheduled(fixedDelay = 8 * 1000)
    private void start() {
        Set<String> contexts = GlobalSession.getContextSessions().keySet();
        contexts.forEach(this::pushByContext);
    }


    private void pushByContext(String context) {
        List<JenkinsBuildHistory> builds = jenkinsService.builds(context);

    }

    public void startBuild(@NonNull String context) {
        try {
            jenkinsClient.build(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
