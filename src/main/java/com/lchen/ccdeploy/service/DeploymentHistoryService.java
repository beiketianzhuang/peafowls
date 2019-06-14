package com.lchen.ccdeploy.service;

import com.lchen.ccdeploy.dao.DeploymentHistoryRepository;
import com.lchen.ccdeploy.model.DeploymentHistory;
import com.lchen.ccdeploy.model.DeploymentProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : lchen
 * @date : 2019/6/12
 */
@Service
public class DeploymentHistoryService {

    @Autowired
    private DeploymentHistoryRepository deploymentHistoryRepository;

    public DeploymentHistory save(Integer version,
                                  DeploymentProcess process, Long deployId) {
        return deploymentHistoryRepository.save(DeploymentHistory.builder()
                .deployId(deployId)
                .status(process)
                .buildVersion(version)
                .build());
    }

}
