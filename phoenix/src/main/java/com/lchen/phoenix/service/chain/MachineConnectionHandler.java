package com.lchen.phoenix.service.chain;

/**
 * @author : lchen
 * @date : 2019/6/11
 */
public abstract class MachineConnectionHandler {
    protected MachineConnectionHandler chain;

    public abstract void handleDeploy(String context, Integer version);

    public MachineConnectionHandler getChain() {
        return chain;
    }

    public void setChain(MachineConnectionHandler chain) {
        this.chain = chain;
    }

}
