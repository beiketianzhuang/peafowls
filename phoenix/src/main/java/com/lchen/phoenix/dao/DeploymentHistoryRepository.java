package com.lchen.phoenix.dao;

import com.lchen.phoenix.model.DeploymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : lchen
 * @date : 2019/6/12
 */
public interface DeploymentHistoryRepository extends JpaRepository<DeploymentHistory, Long> {

}
