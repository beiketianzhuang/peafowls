package com.lchen.ccdeploy.dao;

import com.lchen.ccdeploy.model.DeployPassword;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : lchen
 * @date : 2019/6/11
 */
public interface DeployPasswordRepository extends JpaRepository<DeployPassword, Long> {

    DeployPassword findByContext(String context);
}
