package com.lchen.ccdeploy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author : lchen
 * @date : 2019/6/12
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cc_deploy_history")
public class DeploymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long deployId;
    private Integer buildVersion;

    @Enumerated(EnumType.STRING)
    private DeploymentProcess status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "deployId",insertable = false,updatable = false)
    private DeploymentResult result;

}
