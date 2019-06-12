package com.lchen.ccdeploy.model;

/**
 * @author : lchen
 * @date : 2019/6/12
 */
public enum DeploymentProcess {

    //
    START("部署开始"),
    STOPSERVICESTART("停止服务开始"),
    STOPSERVICESUCCESS("停止服务成功"),
    STOPSERVICEFAILED("停止服务失败"),
    DELETE_PACKAGE_START("删除旧包开始"),
    DELETE_PACKAGE_SUCCESS("删除旧包成功"),
    DELETE_PACKAGE_FAILED("删除旧包失败"),
    COPY_PACKAGE_START("拷贝包开始"),
    COPY_PACKAGE_SUCCESS("拷贝包成功"),
    COPY_PACKAGE_FAILED("拷贝包失败"),
    SERVICE_RUN("服务启动开始"),
    SERVICE_RUN_SUCCESS("服务启动成功"),
    SERVICE_RUN_FAILED("服务启动失败"),
    SERVICE_VERIFY_RUN("部署验证中"),
    SERVICE_VERIFY_SUCCESS("部署验证成功"),
    SERVICE_VERIFY_FAILED("部署验证失败"),
    SUCCESS("部署成功"),
    FAILED("部署失败");

    private String process;

    DeploymentProcess(String process) {
        this.process = process;
    }

}
