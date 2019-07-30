package com.lchen.phoenix.service;

import com.lchen.phoenix.dao.ContextRepository;
import com.lchen.phoenix.model.Context;
import com.lchen.phoenix.service.deploy.DeployHelper;
import com.lchen.phoenix.service.factory.CommonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

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
        if (isNull(byContext)) {
            //todo
            return;
        }
        DeployHelper deployHelper = commonFactory.getBean(byContext.getContextType().getBeanName());
        deployHelper.deploy(context, version);

    }
}
