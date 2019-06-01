package com.lchen.ccdeploy.service;

import com.google.common.collect.Lists;
import com.lchen.ccdeploy.dao.JenkinsBuildHistoryRepository;
import com.lchen.ccdeploy.model.JenkinsBuild;
import com.lchen.ccdeploy.model.JenkinsBuildHistory;
import com.lchen.ccdeploy.model.constants.JenkinsBuildStatus;
import com.lchen.ccdeploy.utils.BuildProgress;
import com.lchen.ccdeploy.utils.JenkinsClient;
import com.offbytwo.jenkins.model.Build;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.lchen.ccdeploy.model.constants.JenkinsBuildStatus.BUILDING;
import static java.util.stream.Collectors.toList;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@Service
public class JenkinsService {

    @Autowired
    private JenkinsClient jenkinsClient;

    @Autowired
    private JenkinsBuildHistoryRepository jenkinsBuildHistoryRepository;

    public List<JenkinsBuildHistory> builds(String context) {
        List<JenkinsBuildHistory> builds = Lists.newArrayList();
        return builds;
    }


    public List<JenkinsBuild> listJenkinsBuilds(@NonNull String context) {
        List<JenkinsBuildHistory> builds = builds(context);
        return builds.stream().map(this::jenkinsBuildBuilder).collect(toList());
    }

    private JenkinsBuild jenkinsBuildBuilder(JenkinsBuildHistory jenkinsBuildHistory) {
        JenkinsBuildStatus buildStatus = jenkinsBuildHistory.getBuildStatus();
        JenkinsBuild jenkinsBuild = JenkinsBuild.apply(jenkinsBuildHistory);
        if (buildStatus == BUILDING) {
            try {
                BuildProgress buildProgress = jenkinsClient.buildProgress(jenkinsBuildHistory.getJobName(), jenkinsBuildHistory.getVersion());
                jenkinsBuild.setPercentage(buildProgress.getExecutor());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jenkinsBuild;
    }

    public List<Integer> deployVersions(String context) {
        return null;
    }

    public void createOrUpdate(JenkinsBuildHistory buildHistory) {

    }
}
