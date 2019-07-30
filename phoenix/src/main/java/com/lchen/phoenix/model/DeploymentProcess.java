package com.lchen.phoenix.model;

/**
 * @author : lchen
 * @date : 2019/6/12
 */
public enum DeploymentProcess {

    //
    START("部署开始"),
    CONNECT_START("连接服务器开始"),
    CONNECT_SUCCESS("连接服务器成功"),
    CONNECT_FAILED("连接服务器失败"),
    STOP_SERVICE_START("停止服务开始"),
    STOP_SERVICE_SUCCESS("停止服务成功"),
    STOP_SERVICE_FAILED("停止服务失败"),
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

    public String getProcess() {
        return this.process;
    }

    public String getColor() {
        if (this == SUCCESS ) {
            return "green";
        }
        if (this == FAILED) {
            return "red";
        }
        return "black";
    }
    public String getBadge() {
        if (this == SUCCESS ) {
            return "success";
        }
        if (this == FAILED) {
            return "failed";
        }
        return "info";
    }

}
