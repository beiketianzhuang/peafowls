package com.lchen.ccdeploy.service;


import com.lchen.ccdeploy.dao.ContextRepository;
import com.lchen.ccdeploy.model.Context;
import com.lchen.ccdeploy.model.ContextReq;
import com.lchen.ccdeploy.model.DeployType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContextService {
    @Autowired
    private ContextRepository contextRepository;

    public void saveContext(ContextReq contextReq) {
        Context context = new Context();
        BeanUtils.copyProperties(contextReq, context);
        DeployType deployType = context.getDeployType();
        if (deployType == DeployType.KUBERNETES) {
            contextRepository.save(context);
        }
    }

    public List<Context> listContexts() {
        return contextRepository.findAll();
    }

    public Context findContext(Long id) {
        Optional<Context> contextOptional = contextRepository.findById(id);
        return contextOptional.orElse(new Context());
    }
}
