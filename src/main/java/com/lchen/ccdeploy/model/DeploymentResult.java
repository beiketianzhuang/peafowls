package com.lchen.ccdeploy.model;

import com.lchen.ccdeploy.model.constants.DeploymentStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author : lchen
 * @date : 2019/6/1
 */
@Entity
@Data
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
