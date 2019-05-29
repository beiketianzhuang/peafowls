package com.lchen.ccdeploy.config;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@Configuration
public class JenkinsConfig {

    @Bean
    public JenkinsServer jenkinsServer(@Value("jenkins.url") String url,
                                       @Value("jenkins.username") String username,
                                       @Value("jenkins.password") String password) {
        JenkinsHttpClient jenkinsHttpClient = new JenkinsHttpClient(URI.create(url), username, password);
        return new JenkinsServer(jenkinsHttpClient);
    }
}
