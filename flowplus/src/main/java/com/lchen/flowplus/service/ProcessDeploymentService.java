package com.lchen.flowplus.service;

import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProcessDeploymentService {
    @Autowired
    private RepositoryService repositoryService;

    public String deploy(MultipartFile processFile) throws IOException {
        return repositoryService.createDeployment()
                .addInputStream(processFile.getOriginalFilename(), processFile.getInputStream())
                .deploy()
                .getId();
    }


    public Long count() {
        return repositoryService.createProcessDefinitionQuery().count();
    }
}
