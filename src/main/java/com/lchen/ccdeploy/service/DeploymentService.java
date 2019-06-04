package com.lchen.ccdeploy.service;

import com.google.common.collect.Lists;
import com.lchen.ccdeploy.dao.DeploymentRepository;
import com.lchen.ccdeploy.model.DeploymentResult;
import com.lchen.ccdeploy.model.DeploymentVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : lchen
 * @date : 2019/6/1
 */
@Service
public class DeploymentService {

    @Autowired
    private DeploymentRepository deploymentRepository;

    public List<DeploymentVersion> deploymentVersions(String context) {
        List<DeploymentResult> deploymentResult = deploymentRepository.findAllByContextCanDeployVersion(context);
        return deploymentResult.stream().map(DeploymentVersion::apply).collect(Collectors.toList());
    }

    public List<DeploymentResult> listDeploymentResult(String context) {
        return Lists.newArrayList();
    }
}
