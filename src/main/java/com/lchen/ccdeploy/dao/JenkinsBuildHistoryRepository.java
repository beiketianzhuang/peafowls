package com.lchen.ccdeploy.dao;

import com.lchen.ccdeploy.model.JenkinsBuildHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JenkinsBuildHistoryRepository extends JpaRepository<JenkinsBuildHistory, Long> {

}
