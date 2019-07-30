package com.lchen.phoenix.model;

import com.lchen.phoenix.model.constants.JenkinsBuildStatus;
import com.offbytwo.jenkins.model.BuildChangeSet;
import com.offbytwo.jenkins.model.BuildChangeSetItem;
import com.offbytwo.jenkins.model.BuildWithDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@Entity
@Data
@Builder
@Table(name = "cc_deploy_jenkins_history")
@AllArgsConstructor
@NoArgsConstructor
public class JenkinsBuildHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobName;
    @Enumerated(value = EnumType.STRING)
    private JenkinsBuildStatus buildStatus;
    private String codeChange;
    private Integer version;
    private Long buildTime;

    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static JenkinsBuildHistory buildHistory(BuildWithDetails details, String context) {

        return JenkinsBuildHistory.builder()
                .buildStatus(details.isBuilding() ? JenkinsBuildStatus.BUILDING : JenkinsBuildStatus.valueOf(details.getResult().name()))
                .version(details.getNumber())
                .codeChange(codeChange(details.getChangeSets()))
                .jobName(context)
                .buildTime(details.getDuration())
                .build();
    }

    public static String codeChange(List<BuildChangeSet> changeSets) {
        if (changeSets != null) {
            List<String> msg = changeSets.stream()
                    .flatMap(changeSet -> changeSet.getItems().stream().map(BuildChangeSetItem::getMsg))
                    .collect(toList());
            return StringUtils.join(msg, "<br>");
        }
        return EMPTY;
    }

}
