package com.lchen.ccdeploy.service;

import com.google.common.collect.Lists;
import com.lchen.ccdeploy.model.DeploymentResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : lchen
 * @date : 2019/6/1
 */
@Service
public class DeploymentService {


    public List<DeploymentResult> listDeploymentResult(String context) {

        return Lists.newLinkedList();
    }
}
