package com.lchen.phoenix.service;

import com.lchen.phoenix.dao.DeploymentHistoryRepository;
import com.lchen.phoenix.model.DeploymentHistory;
import com.lchen.phoenix.model.DeploymentProcess;
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
