package com.lchen.flowplus.controller;

import com.lchen.flowplus.service.ProcessDeploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ProcessDeploymentController {

    @Autowired
    private ProcessDeploymentService processDeploymentService;

    @PostMapping(value = "/process/deployment")
    public String deploy(@RequestParam(value = "file") MultipartFile processFile) throws IOException {
        return  processDeploymentService.deploy(processFile);
    }

    @GetMapping(value = "/process/deployment")
    public Long deploymentCount() {
        return processDeploymentService.count();
    }
}
