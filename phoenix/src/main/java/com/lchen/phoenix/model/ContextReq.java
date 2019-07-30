package com.lchen.phoenix.model;

import com.lchen.ccdeploy.model.constants.ContextType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author : lchen
 * @date : 2019/6/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContextReq {

    private String context;
    private String jobName;
    private ContextType contextType;
    private String department;
    private Integer buildCount;
    @Enumerated(EnumType.STRING)
    private DeployType deployType;
    private Integer deploymentStrategy;
    private String kubernetesConfig;

    private PasswordLogin passwordLogin;

    @Data
    class PasswordLogin {
        private String ip;
        private String username;
        private String password;
        private Integer port;
    }

    class SshLogin {

    }
}
