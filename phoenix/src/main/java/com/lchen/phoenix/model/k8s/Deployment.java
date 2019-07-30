package com.lchen.phoenix.model.k8s;

import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author : lchen
 * @date : 2019/7/15
 */
@Data
public class Deployment {
    private String namespace;

    public io.fabric8.kubernetes.api.model.apps.Deployment copyToK8sDeployment() {
        io.fabric8.kubernetes.api.model.apps.Deployment deployment = new io.fabric8.kubernetes.api.model.apps.Deployment();
        BeanUtils.copyProperties(this, deployment);
        return deployment;
    }
}
