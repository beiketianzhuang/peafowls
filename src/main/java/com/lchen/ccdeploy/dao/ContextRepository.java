package com.lchen.ccdeploy.dao;

import com.lchen.ccdeploy.model.Context;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : lchen
 * @date : 2019/6/11
 */
public interface ContextRepository extends JpaRepository<Context, Long> {

    Context findByContext(String context);
}
