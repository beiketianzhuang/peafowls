package com.lchen.ccdeploy.controller;

import com.lchen.ccdeploy.service.DeployHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@RestController
public class DeployController {

    @Autowired
    private DeployHelper deployHelper;

    /**
     * 部署
     * @param context
     * @param buildId
     */
    @PostMapping(value = "/contexts/deploy/{context}")
    public void deploy(@PathVariable("context") String context,
                       @RequestParam("buildId") Integer buildId) {
        deployHelper.deploy(context,buildId);
    }

    /**
     * 重启
     * @param context
     */
    @PutMapping(value = "/contexts/deploy/{context}")
    public void restartDeploy(@PathVariable("context") String context) {

    }

    /**
     * 部署验证接口，用于被部署项目的部署完成后的回调接口
     * @param context
     */
    @GetMapping(value = "/contexts/deploy/{context}")
    public void deploy(@PathVariable("context") String context) {

    }
}
