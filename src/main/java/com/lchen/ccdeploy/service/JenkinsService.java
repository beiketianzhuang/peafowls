package com.lchen.ccdeploy.service;

import com.lchen.ccdeploy.dao.JenkinsBuildHistoryRepository;
import com.lchen.ccdeploy.model.JenkinsBuildHistory;
import com.lchen.ccdeploy.utils.JenkinsClient;
import com.offbytwo.jenkins.model.Build;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

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

        return null;
    }

    public void update(String context) {
        try {
            List<Build> builds = jenkinsClient.buildsByJob(context);
            for (Build build : builds) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Integer> deployVersions(String context) {
        return null;
    }
}
