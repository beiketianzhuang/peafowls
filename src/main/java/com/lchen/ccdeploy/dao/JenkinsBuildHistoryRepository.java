package com.lchen.ccdeploy.dao;

import com.lchen.ccdeploy.model.JenkinsBuildHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JenkinsBuildHistoryRepository extends JpaRepository<JenkinsBuildHistory, Long> {

    JenkinsBuildHistory findByVersionAndJobName(Integer version, String jobName);

    @Query(value = "select * from cc_deploy_jenkins_history where job_name=?1 order by version desc limit 10", nativeQuery = true)
    List<JenkinsBuildHistory> findLatelyByVersionAndJobName(String jobName);

}
