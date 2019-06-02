package com.lchen.ccdeploy.model.constants;

public enum JenkinsBuildStatus {
    //成功
    SUCCESS("构建成功", "green", "success"),
    BUILDING("构建中","unknown","info"),
    QUEUE("排队中","black","info"),
    FAILURE("构建失败", "red", "danger"),
    UNSTABLE("构建不稳定","#E6A23C","warning"),
    ABORTED("被取消","black","info");
    public String statusCh;
    public String color;
    public String badge;

    private JenkinsBuildStatus(String statusCh, String color, String badge) {
        this.statusCh = statusCh;
        this.color = color;
        this.badge = badge;
    }


}
