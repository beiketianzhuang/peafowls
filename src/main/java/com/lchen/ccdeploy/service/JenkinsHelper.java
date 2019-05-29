package com.lchen.ccdeploy.service;

import com.lchen.ccdeploy.utils.JenkinsClient;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@Component
public class JenkinsHelper {

    @Autowired
    private JenkinsClient jenkinsClient;

    public void startBuild(@NonNull String context) {
        try {
            jenkinsClient.build(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
