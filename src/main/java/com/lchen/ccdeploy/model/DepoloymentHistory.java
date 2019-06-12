package com.lchen.ccdeploy.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author : lchen
 * @date : 2019/6/12
 */
@Data
@Entity
@Table(name = "cc_deploy_history")
public class DepoloymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long deployId;
    private Integer buildVersion;

    @Enumerated(EnumType.STRING)
    private DeploymentProcess status;

}
