package com.lchen.ccdeploy.model;

import com.google.common.collect.Lists;
import com.lchen.ccdeploy.model.constants.DeploymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.lchen.ccdeploy.model.DeploymentResult.Result.apply;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * @author : lchen
 * @date : 2019/6/1
 */
@Entity
@Data
@Builder
@Table(name = "cc_deploy_result")
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String context;
    private Integer buildVersion;
    @Enumerated(value = EnumType.STRING)
    private DeploymentStatus deployStatus;
    private String username;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "deployId", referencedColumnName = "id")
    private List<DeploymentHistory> deploymentHistories;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Transient
    private Result result;

    public static DeploymentResult buildResult(DeploymentResult deploymentResult) {
        List<DeploymentHistory> deploymentHistories = deploymentResult.getDeploymentHistories();
        deploymentResult.setResult(apply(deploymentHistories));
        return deploymentResult;
    }

    @Data
    @Builder
    public static class Result {
        private String color;
        private String badge;
        private List<String> status;

        public static Result apply(List<DeploymentHistory> deploymentHistories) {
            if (deploymentHistories.size() == 0) {
                return Result.builder()
                        .status(Lists.newArrayList())
                        .color(EMPTY)
                        .badge("success")
                        .build();
            }
            DeploymentHistory deploymentHistory = deploymentHistories.get(deploymentHistories.size() - 1);
            List<String> status = deploymentHistories.stream()
                    .map(DeploymentHistory::getStatus)
                    .map(DeploymentProcess::getProcess)
                    .collect(toList());
            return Result.builder()
                    .status(status)
                    .color("green")
                    .badge("success")
                    .build();

        }
    }

}
