package com.lchen.ccdeploy.model;

import com.lchen.ccdeploy.model.constants.DeploymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
