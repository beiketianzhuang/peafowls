package com.lchen.ccdeploy;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.*;
import org.junit.Test;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
public class JenkinsTest {

    @Test
    public void test1() throws Exception{
        final JenkinsHttpClient jenkinsHttpClient = new JenkinsHttpClient(URI.create("http://ci.purang.com"),"chencheng3","Aai03056");
        JenkinsServer jenkinsServer = new JenkinsServer(jenkinsHttpClient);
        Queue queue = jenkinsServer.getQueue();
        JobWithDetails job = jenkinsServer.getJob("testss");
//        List<Build> builds = job.getBuilds();
        job.getQueueItem();
//        job.getQueueItem();
//        for (Build build : builds) {
////            BuildWithDetails details = build.details();
//            BuildWithDetails details = build.details();
//            BuildResult result = details.getResult();
//            System.out.println(result);
//        }
    }

    @Test
    public void timeTest() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        System.out.println(now.toLocalDate());
        System.out.println(Instant.now());
        System.out.println(System.currentTimeMillis());
    }

}
