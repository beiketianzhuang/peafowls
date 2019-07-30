package com.lchen.phoenix.model;

import com.lchen.phoenix.model.constants.ContextType;
import lombok.Data;

import javax.persistence.*;

/**
 * @author : lchen
 * @date : 2019/6/11
 */
@Entity
@Data
@Table(name = "cc_deploy_context")
public class Context {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String context;
    private String jobName;
    @Enumerated(EnumType.STRING)
    private ContextType contextType;
    private String department;
    @Enumerated(EnumType.STRING)
    private DeployType deployType;
    private Integer deploymentStrategy;
    private String kubernetesConfig;
}
