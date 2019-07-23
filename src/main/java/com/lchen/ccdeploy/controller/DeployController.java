package com.lchen.ccdeploy.controller;

import com.lchen.ccdeploy.model.DeploymentVersion;
import com.lchen.ccdeploy.model.req.DeploymentReq;
import com.lchen.ccdeploy.service.DeployClient;
import com.lchen.ccdeploy.service.DeploymentService;
import com.lchen.ccdeploy.utils.KubernetesClients;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@RestController
public class DeployController {

    @Autowired
    private DeployClient deployClient;
    @Autowired
    private DeploymentService deploymentService;
    @Autowired
    private KubernetesClients kubernetesClients;
    @Autowired
    private KubernetesClient client;

    /**
     * 部署
     *
     * @param context
     * @param deployVersion
     */
    @PostMapping(value = "/contexts/deploy/{context}")
    public void deploy(@PathVariable("context") String context,
                       @RequestParam("deployVersion") Integer deployVersion) {
        deployClient.deploy(context, deployVersion);
    }

    /**
     * 重启
     *
     * @param context
     */
    @PutMapping(value = "/contexts/deploy/{context}")
    public void restartDeploy(@PathVariable("context") String context) {

    }

    /**
     * 部署验证接口，用于被部署项目的部署完成后的回调接口
     *
     * @param context
     */
    @GetMapping(value = "/contexts/deploy/{context}")
    public void deploy(@PathVariable("context") String context) {

    }

    @GetMapping(value = "/contexts/deploy/version/{context}")
    public List<DeploymentVersion> deployVersion(@PathVariable("context") String context) {
        return deploymentService.deploymentVersions(context);
    }

    /**
     * 自动部署
     *
     */
    @PostMapping(value = "/contexts/deploy/auto")
    public void autoDeploy(@RequestBody DeploymentReq deploymentReq) {
        deploymentService.autoDeploy(deploymentReq);
    }
}
