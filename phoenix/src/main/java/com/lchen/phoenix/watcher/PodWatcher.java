package com.lchen.phoenix.watcher;

import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watcher;
import org.springframework.stereotype.Component;

/**
 * @author : lchen
 * @date : 2019/7/17
 */
@Component
public class PodWatcher implements Watcher {

    @Override
    public void eventReceived(Action action, Object resource) {
        System.out.println(action + "==============================================" + resource);
    }

    @Override
    public void onClose(KubernetesClientException cause) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

}
