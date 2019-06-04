package com.lchen.ccdeploy.dao;

import com.lchen.ccdeploy.model.DeploymentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author : lchen
 * @date : 2019/6/3
 */
public interface DeploymentRepository extends JpaRepository<DeploymentResult, Long> {

    @Query(value = "SELECT d.id,d.context,d.deploy_status,d.build_version,d.created_at,d.updated_at FROM (SELECT MAX(r.id) id FROM cc_deploy_result r " +
            "INNER JOIN (SELECT * FROM cc_deploy_jenkins_history " +
            "WHERE job_name = ?1 AND build_status IN ('UNSTABLE','SUCCESS') " +
            "ORDER BY `version` DESC LIMIT 5) h ON r.context = h.job_name AND r.build_version=h.version " +
            "GROUP BY r.context,r.build_version) a JOIN cc_deploy_result d ON d.id = a.id", nativeQuery = true)
    List<DeploymentResult> findAllByContextCanDeployVersion(String context);
}
