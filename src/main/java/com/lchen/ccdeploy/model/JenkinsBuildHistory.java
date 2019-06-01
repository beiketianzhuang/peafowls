package com.lchen.ccdeploy.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@Data
public class JenkinsBuildHistory {
    /**
     * `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
     *   `job_name` bigint(11) NOT NULL,
     *   `build_status` varchar(50) NOT NULL,
     *   `build_time` int(11) DEFAULT '0',
     *   `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
     *   `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



}
