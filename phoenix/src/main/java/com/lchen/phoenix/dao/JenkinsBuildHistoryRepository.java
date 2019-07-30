package com.lchen.phoenix.dao;

import com.lchen.phoenix.model.JenkinsBuildHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JenkinsBuildHistoryRepository extends JpaRepository<JenkinsBuildHistory, Long> {

    JenkinsBuildHistory findByVersionAndJobName(Integer version, String jobName);

    @Query(value = "select * from cc_deploy_jenkins_history where job_name=?1 order by version desc limit 10", nativeQuery = true)
    List<JenkinsBuildHistory> findLatelyByVersionAndJobName(String jobName);

    @Query(value = "SELECT * FROM cc_deploy_jenkins_history WHERE job_name = ?1 " +
            "and build_status IN ('UNSTABLE','SUCCESS') ORDER BY `version` DESC LIMIT 5",nativeQuery = true)
    List<JenkinsBuildHistory> findDeployVersionAndJobName(String jobName);
}
