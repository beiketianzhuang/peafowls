package com.lchen.ccdeploy.dao;

import com.lchen.ccdeploy.model.DeploymentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author : lchen
 * @date : 2019/6/3
 */
public interface DeploymentRepository extends JpaRepository<DeploymentResult, Long> {

    @Query(value = "SELECT * FROM cc_deploy_result WHERE context=?1 AND build_version=?2 ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<DeploymentResult> findAllByContextCanDeployVersion(String context, Integer version);

    @Query(value = "SELECT * FROM cc_deploy_result WHERE context = ?1 ORDER BY id DESC LIMIT 10", nativeQuery = true)
    List<DeploymentResult> findTopTenByContext(String context);
}
