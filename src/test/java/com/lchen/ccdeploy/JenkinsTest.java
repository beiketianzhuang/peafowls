package com.lchen.ccdeploy;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.*;
import org.junit.Test;

import java.net.URI;
import java.util.List;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
public class JenkinsTest {

    @Test
    public void test1() throws Exception{
        final JenkinsHttpClient jenkinsHttpClient = new JenkinsHttpClient(URI.create("http://10.10.31.25:8080"),"root","123456");
        JenkinsServer jenkinsServer = new JenkinsServer(jenkinsHttpClient);
        JobWithDetails job = jenkinsServer.getJob("sqcsbackend_prod");
        List<Build> builds = job.getBuilds();
        for (Build build : builds) {
//            BuildWithDetails details = build.details();
            BuildWithDetails details = build.details();
            BuildResult result = details.getResult();
            System.out.println(result);
        }
    }

}
