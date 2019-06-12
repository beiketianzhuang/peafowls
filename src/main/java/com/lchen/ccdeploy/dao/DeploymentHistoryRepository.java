package com.lchen.ccdeploy.dao;

import com.lchen.ccdeploy.model.DeploymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : lchen
 * @date : 2019/6/12
 */
public interface DeploymentHistoryRepository extends JpaRepository<DeploymentHistory, Long> {

}
