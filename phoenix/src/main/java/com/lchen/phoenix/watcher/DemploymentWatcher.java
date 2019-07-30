package com.lchen.phoenix.watcher;

import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watcher;

/**
 * @author : lchen
 * @date : 2019/7/17
 */
public class DemploymentWatcher implements Watcher {


    @Override
    public void eventReceived(Action action, Object resource) {
        System.out.println(resource);
    }

    @Override
    public void onClose(KubernetesClientException cause) {

    }

}
