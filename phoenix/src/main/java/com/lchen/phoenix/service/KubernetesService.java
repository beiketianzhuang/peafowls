package com.lchen.phoenix.service;

import com.lchen.phoenix.utils.KubernetesClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : lchen
 * @date : 2019/7/16
 */
@Service
public class KubernetesService {

    private static final String DEFAULT_NAMESPACE = "default";

    @Autowired
    private KubernetesClients kubernetesClients;

    public int getReplicas(String namespace, String context) {
        return kubernetesClients.getReplicas(namespace, context);
    }

    public int getReplicas(String context) {
        return getReplicas(DEFAULT_NAMESPACE, context);
    }

}
