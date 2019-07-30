package com.lchen.phoenix.service;


import com.lchen.phoenix.dao.ContextRepository;
import com.lchen.phoenix.model.Context;
import com.lchen.phoenix.model.ContextReq;
import com.lchen.phoenix.model.DeployType;
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
