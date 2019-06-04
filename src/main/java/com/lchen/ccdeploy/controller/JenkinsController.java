package com.lchen.ccdeploy.controller;

import com.lchen.ccdeploy.model.DeploymentVersion;
import com.lchen.ccdeploy.model.JenkinsBuildHistory;
import com.lchen.ccdeploy.service.JenkinsHelper;
import com.lchen.ccdeploy.service.JenkinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@RestController
public class JenkinsController {

    @Autowired
    private JenkinsHelper jenkinsHelper;
    @Autowired
    private JenkinsService jenkinsService;

    @PostMapping(value = "/jenkins/contexts/build/{context}")
    public void startBuild(@PathVariable("context") String context) {
        jenkinsHelper.startBuild(context);
    }

    /**
     *
     * @param context
     * @return
     */
    @GetMapping(value = "/jenkins/contexts/builds/{context}")
    public List<JenkinsBuildHistory> builds(@PathVariable("context") String context) {
        return jenkinsService.builds(context);
    }


    /**
     * 取消构建
     *
     * @param context
     * @param id
     */
    @PutMapping(value = "/jenkins/contexts/build/{context}")
    public void stopBuild(@PathVariable("context") String context, @RequestParam("id") Long id) {

    }

}
