package com.lchen.ccdeploy.model;

import com.lchen.ccdeploy.model.constants.Env;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author : lchen
 * @date : 2019/6/11
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DeployPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String context;
    private String ip;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Env env;
    private Integer port;

}
