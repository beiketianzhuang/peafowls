package com.lchen.phoenix.service.deploy;

import com.lchen.phoenix.service.chain.MachineConnectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : lchen
 * @date : 2019/6/11
 */
@Component("springBootContext")
public class SpringBootDeployHelper implements DeployHelper {

    @Autowired
    private MachineConnectionClient machineConnectionClient;

    @Override
    public void deploy(String context, Integer version) {
        machineConnectionClient.handle(context, version);
    }

}
