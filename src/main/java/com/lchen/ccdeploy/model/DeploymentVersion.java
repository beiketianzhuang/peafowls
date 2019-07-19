package com.lchen.ccdeploy.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author : lchen
 * @date : 2019/6/3
 */
@Data
@Builder
public class DeploymentVersion {
    private Integer version;
    private String deployStatus;
    private boolean showSelect;

    public static DeploymentVersion apply(DeploymentResult result) {
        return DeploymentVersion.builder()
                .version(result.getBuildVersion())
                .deployStatus(result.getDeployStatus().deployStatus)
                .build();
    }

    public static DeploymentVersion applyBuild(JenkinsBuildHistory buildHistory) {
        return DeploymentVersion.builder()
                .version(buildHistory.getVersion())
                .deployStatus(null)
                .build();
    }
}
