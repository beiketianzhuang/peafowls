package com.lchen.ccdeploy.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

/**
 * @author : lchen
 * @date : 2019/6/12
 */
@Data
@Entity
@Builder
@Table(name = "cc_deploy_history")
public class DeploymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long deployId;
    private Integer buildVersion;

    @Enumerated(EnumType.STRING)
    private DeploymentProcess status;

}
