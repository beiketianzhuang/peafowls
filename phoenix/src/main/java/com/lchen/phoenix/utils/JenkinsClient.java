package com.lchen.phoenix.utils;

import com.google.common.collect.Lists;
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
import java.util.Optional;

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
        JobWithDetails job = job(jobName);
        if (job == null) throw new RuntimeException("该jobName不存在");
        job.build(true);
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
    public void queues(@NonNull String jobName, Integer version) throws IOException {
        Queue queue = jenkinsServer.getQueue();
        List<QueueItem> items = queue.getItems();
        QueueItem queueItem = jenkinsServer.getJob(jobName).getQueueItem();

    }

    public Optional<Long> queueing(@NonNull String jobName) throws IOException {
        JobWithDetails job = job(jobName);
        if (job == null) return Optional.empty();
        QueueItem queueItem = job.getQueueItem();
        if (queueItem == null) return Optional.empty();
        return Optional.of(queueItem.getId());
    }


    private JobWithDetails job(@NonNull String jobName) throws IOException {
        return jenkinsServer.getJob(jobName);
    }

    public List<Build> buildsByJob(@NonNull String jobName) throws IOException {
        JobWithDetails job = job(jobName);
        if (job == null) return Lists.newArrayList();
        return job.getBuilds();
    }


    public BuildProgress buildProgress(String jobName, Integer version) throws IOException {
        return jenkinsHttpClient.get("/job/" + jobName + "/" + version + "?tree=executor[progress]", BuildProgress.class);
    }

}
