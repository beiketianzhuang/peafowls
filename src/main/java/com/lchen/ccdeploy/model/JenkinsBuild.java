package com.lchen.ccdeploy.model;

import com.lchen.ccdeploy.model.constants.JenkinsBuildStatus;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

/**
 * @author : lchen
 * @date : 2019/6/1
 */
@Data
@Builder
public class JenkinsBuild {
    private Long id;
    private String jobName;
    private JenkinsBuildStatus buildStatus;
    private String codeChange;
    private String color;
    @Builder.Default
    private Integer percentage = 100;
    private String badge;
    private String statusCh;
    private Integer version;
    private Long buildTime;
    private String createdAt;
    private String updatedAt;

    public static JenkinsBuild apply(JenkinsBuildHistory details) {
        JenkinsBuildStatus jenkinsBuildStatus = details.getBuildStatus();
        //fixme details.createdAt 在创建后同一个事务做查询，没有值
        return JenkinsBuild.builder()
                .id(details.getId())
                .buildStatus(details.getBuildStatus())
                .version(details.getVersion())
                .codeChange(details.getCodeChange())
                .jobName(details.getJobName())
                .color(jenkinsBuildStatus.color)
                .badge(jenkinsBuildStatus.badge)
                .statusCh(jenkinsBuildStatus.statusCh)
                .buildTime(details.getBuildTime())
                .createdAt(details.getCreatedAt() != null ? details.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null)
                .build();
    }
}
