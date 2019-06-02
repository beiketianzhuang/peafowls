package com.lchen.ccdeploy.utils;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.Queue;
import com.offbytwo.jenkins.model.QueueItem;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
    @Resource
    private JenkinsHttpClient jenkinsHttpClient;

    public void build(@NonNull String jobName) throws IOException {
        jenkinsServer.getJob(jobName).build(true);
    }

    public void pause(@NonNull String jobName, @NonNull Integer buildNumber) throws IOException {
        JobWithDetails job = jenkinsServer.getJob(jobName);
        Build build = job.getBuildByNumber(buildNumber);
        build.Stop();
    }

    /**
     * job排队情况
     *
     * @param jobName
     * @throws IOException
     */
    public void queues(@NonNull String jobName,Integer version) throws IOException {
        Queue queue = jenkinsServer.getQueue();
        List<QueueItem> items = queue.getItems();
        QueueItem queueItem = jenkinsServer.getJob(jobName).getQueueItem();

    }


    public List<Build> buildsByJob(@NonNull String jobName) throws IOException {
        JobWithDetails job = jenkinsServer.getJob(jobName);
        return job.getBuilds();
    }


    public BuildProgress buildProgress(String jobName,Integer version) throws IOException {
        BuildProgress buildProgress = jenkinsHttpClient.get("/job/"+jobName + "/" + version + "?tree=executor[progress]", BuildProgress.class);
        return buildProgress;
    }

}
