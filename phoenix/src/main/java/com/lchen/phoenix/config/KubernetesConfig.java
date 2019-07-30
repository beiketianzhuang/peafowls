package com.lchen.phoenix.config;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : lchen
 * @date : 2019/7/15
 */
@Configuration
public class KubernetesConfig {

    @Bean
    public KubernetesClient kubernetesClient(@Value("${k8s.url}") String url) {
        Config config = new ConfigBuilder().withMasterUrl(url).build();
        return new DefaultKubernetesClient(config);
    }


}
