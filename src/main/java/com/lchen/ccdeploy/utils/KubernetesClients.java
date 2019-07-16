package com.lchen.ccdeploy.utils;

import com.lchen.ccdeploy.model.k8s.Deployment;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toSet;

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
        client.apps()
                .deployments()
                .inNamespace(deployment.getNamespace())
                .createOrReplace(deployment.copyToK8sDeployment());
    }

    public Set<ObjectMeta> listNamespaceObjectMeta() {
        return client.namespaces().list().getItems().stream()
                .map(Namespace::getMetadata)
                .collect(toSet());
    }

    /**
     * 获取所有k8s的名称空间
     *
     * @return
     */
    public Set<String> listNamespaceObjectMetaName() {
        return listNamespaceObjectMeta().stream()
                .map(ObjectMeta::getName)
                .collect(toSet());
    }

    public void deletePod(String namespace, List<Pod> items) {
        client.pods().inNamespace(namespace).delete(items);
    }

    /**
     * 删除普通的pod
     *
     * @param namespace
     * @param pod
     */
    public void deletePod(String namespace, Pod pod) {
        deletePod(namespace, newArrayList(pod));
    }


    public void deleteRc(String namespace, ReplicationController item) {
        deleteRc(namespace, newArrayList(item));
    }

    /**
     * 对于通过ReplicationController创建的pod需要先删除rc
     *
     * @param namespace
     * @param items
     */
    public void deleteRc(String namespace, List<ReplicationController> items) {
        client.replicationControllers().inNamespace(namespace).delete(items);
    }

}
