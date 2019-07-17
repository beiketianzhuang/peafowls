package com.lchen.ccdeploy.watcher;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watcher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : lchen
 * @date : 2019/7/17
 */
@Component
public class KubernetesWatcher implements InitializingBean {

    @Autowired
    private PodWatcher podWatcher;
    @Autowired
    private KubernetesClient client;

    @Override
    public void afterPropertiesSet() throws Exception {
        //监听部署事件
        deploymentWather();
        //监听pod事件
        podWatcher();
    }

    private void podWatcher() {
        client.pods().inNamespace("default").watch(new Watcher<Pod>() {
            @Override
            public void eventReceived(Action action, Pod resource) {
                podWatcher.eventReceived(action, resource);
            }

            @Override
            public void onClose(KubernetesClientException cause) {

            }
        });
    }

    private void deploymentWather() {
//        client.customResources()
    }

}
