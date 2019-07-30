package com.lchen.phoenix.service.chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : lchen
 * @date : 2019/6/11
 */
@Component
public class MachineConnectionClient {
    @Autowired
    private MachineConnectionPasswordHandler machineConnectionPasswordHandler;

    public void handle(String context, Integer version) {
        machineConnectionPasswordHandler.setChain(null);
        machineConnectionPasswordHandler.handleDeploy(context, version);
    }


}
