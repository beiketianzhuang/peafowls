package com.lchen.phoenix.model.constants;

/**
 * @author : lchen
 * @date : 2019/6/3
 */
public enum DeploymentStatus {
    //a
    SUCCESS("部署成功", "success"),
    FAILED("部署失败","danger"),
    DEPLOYING("部署中","deploying");
    public String status;
    public String deployStatus;

    DeploymentStatus(String status, String deployStatus) {
        this.status = status;
        this.deployStatus = deployStatus;
    }
}
