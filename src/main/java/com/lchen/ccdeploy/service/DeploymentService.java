package com.lchen.ccdeploy.service;

import com.lchen.ccdeploy.dao.DeploymentRepository;
import com.lchen.ccdeploy.dao.JenkinsBuildHistoryRepository;
import com.lchen.ccdeploy.model.DeploymentResult;
import com.lchen.ccdeploy.model.DeploymentVersion;
import com.lchen.ccdeploy.model.JenkinsBuildHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * @author : lchen
 * @date : 2019/6/1
 */
@Service
public class DeploymentService {

    @Autowired
    private DeploymentRepository deploymentRepository;
    @Autowired
    private JenkinsBuildHistoryRepository jenkinsBuildHistoryRepository;

    public List<DeploymentVersion> deploymentVersions(String context) {
        List<JenkinsBuildHistory> jenkinsBuildHistories = jenkinsBuildHistoryRepository.findDeployVersionAndJobName(context);
        return jenkinsBuildHistories.stream().map(this::apply).filter(Objects::nonNull).collect(toList());
    }

    public List<DeploymentResult> listDeploymentResult(String context) {
        List<DeploymentResult> deploymentResults = deploymentRepository.findTopTenByContext(context);
        return deploymentResults.stream().map(DeploymentResult::buildResult).collect(toList());
    }

    public DeploymentVersion apply(JenkinsBuildHistory jenkinsBuildHistory) {
        Optional<DeploymentResult> deploymentResultOptional = deploymentRepository.findAllByContextCanDeployVersion(
                jenkinsBuildHistory.getJobName(),
                jenkinsBuildHistory.getVersion());
        return deploymentResultOptional.map(DeploymentVersion::apply).orElse(null);
    }

}
