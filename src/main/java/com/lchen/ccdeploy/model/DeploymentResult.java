package com.lchen.ccdeploy.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : lchen
 * @date : 2019/6/1
 */
@Data
public class DeploymentResult {

    private Long id;
    private Integer buildVersion;
    private String deployStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
