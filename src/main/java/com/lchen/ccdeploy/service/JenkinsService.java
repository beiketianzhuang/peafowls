package com.lchen.ccdeploy.service;

import com.lchen.ccdeploy.dao.JenkinsBuildHistoryRepository;
import com.lchen.ccdeploy.model.JenkinsBuildHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@Service
public class JenkinsService {

    @Autowired
    private JenkinsBuildHistoryRepository jenkinsBuildHistoryRepository;

    public List<JenkinsBuildHistory> builds(String context) {
        return null;
    }

    public List<Integer> deployVersions(String context) {
        return null;
    }
}
