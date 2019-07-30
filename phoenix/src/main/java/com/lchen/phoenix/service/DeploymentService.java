package com.lchen.phoenix.service;

import com.lchen.phoenix.dao.DeploymentRepository;
import com.lchen.phoenix.dao.JenkinsBuildHistoryRepository;
import com.lchen.phoenix.model.DeploymentResult;
import com.lchen.phoenix.model.DeploymentVersion;
import com.lchen.phoenix.model.JenkinsBuildHistory;
import com.lchen.phoenix.model.req.DeploymentReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * @author : lchen
 * @date : 2019/6/1
 */
@Service
public class DeploymentService {

    @Autowired
    private DeployClient deployClient;
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
        return DeploymentVersion.applyBuild(jenkinsBuildHistory);
    }

    public void autoDeploy(DeploymentReq deploymentReq) {
        //写入一条部署开始日志

        //开始部署
        deployClient.deploy(deploymentReq.getContext(),deploymentReq.getDeployVersion());

    }
}
