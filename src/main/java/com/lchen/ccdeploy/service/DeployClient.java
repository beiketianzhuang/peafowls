package com.lchen.ccdeploy.service;

import com.lchen.ccdeploy.dao.ContextRepository;
import com.lchen.ccdeploy.model.Context;
import com.lchen.ccdeploy.service.deploy.DeployHelper;
import com.lchen.ccdeploy.service.factory.CommonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@Component
public class DeployClient {
    @Autowired
    private ContextRepository contextRepository;
    @Autowired
    private CommonFactory commonFactory;

    public void deploy(String context, Integer version) {
        Context byContext = contextRepository.findByContext(context);
        DeployHelper deployHelper = commonFactory.getBean(byContext.getContextType().getBeanName());
        deployHelper.deploy(context, version);

    }
}
