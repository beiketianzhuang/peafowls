package com.lchen.ccdeploy;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import org.junit.Test;

import java.net.URI;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
public class JenkinsTest {

    @Test
    public void test1() throws Exception{
        JenkinsHttpClient jenkinsHttpClient = new JenkinsHttpClient(URI.create("http://localhost:8080"),"root","root");
        JenkinsServer jenkinsServer = new JenkinsServer(jenkinsHttpClient);
        jenkinsServer.getJob("demo").build(true);
    }

}
