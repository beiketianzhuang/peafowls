package com.lchen.ccdeploy.utils;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Queue;
import com.offbytwo.jenkins.model.QueueItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@Component
public class JenkinsClient {
    @Autowired
    private JenkinsServer jenkinsServer;

    public void build(String context) throws IOException {
        jenkinsServer.getJob(context).build(true);
    }

    public void pause(String context) throws IOException{
        //todo 可能需要其它参数
        jenkinsServer.getJob("").getBuilds();
    }

    /**
     * job排队情况
     * @param context
     * @throws IOException
     */
    public void queues(String context) throws IOException{
        Queue queue = jenkinsServer.getQueue();
        List<QueueItem> items = queue.getItems();
    }



}
