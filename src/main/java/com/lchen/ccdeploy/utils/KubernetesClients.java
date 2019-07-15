package com.lchen.ccdeploy.utils;

import com.lchen.ccdeploy.model.k8s.Deployment;
import io.fabric8.kubernetes.api.model.ReplicationController;
import io.fabric8.kubernetes.api.model.ReplicationControllerSpec;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * @author : lchen
 * @date : 2019/7/15
 */
@Component
public class KubernetesClients {

    @Autowired
    private KubernetesClient client;

    public Optional<ReplicationController> replicationController(String namespace, String context) {
        ReplicationController pod = client.replicationControllers().inNamespace(namespace).withName(context).get();
        if (Objects.isNull(pod)) {
            return Optional.empty();
        }
        return Optional.of(pod);
    }

    /**
     * 获取应用的副本数（也可以指定为pod的数目：非running的pod）
     *
     * @param namespace
     * @param context
     * @return
     */
    public Integer getReplicas(String namespace, String context) {
        return replicationController(namespace, context)
                .map(ReplicationController::getSpec)
                .map(ReplicationControllerSpec::getReplicas)
                .orElse(0);
    }

    /**
     * 部署应用
     *
     * @param deployment
     */
    public void deploy(Deployment deployment) {
        client.apps().deployments().inNamespace(deployment.getNamespace()).createOrReplace(null);
    }
}
