package com.lchen.ccdeploy.model;

import com.lchen.ccdeploy.model.constants.JenkinsBuildStatus;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@Entity
@Data
@Builder
public class JenkinsBuildHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobName;
    private JenkinsBuildStatus buildStatus;
    private String codeChange;
    private Integer version;
    private Long buildTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static JenkinsBuildHistory buildHistory(BuildWithDetails details, BuildResult result) {

        return JenkinsBuildHistory.builder()
                .buildStatus(JenkinsBuildStatus.valueOf(result.name()))
                .version(details.getNumber())
                .codeChange(details.getChangeSets().toString())
                .jobName(details.getFullDisplayName())
                .build();
    }

}
