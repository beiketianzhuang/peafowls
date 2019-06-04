package com.lchen.ccdeploy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String contextType;
    private String department;
    private Integer buildCount;
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
