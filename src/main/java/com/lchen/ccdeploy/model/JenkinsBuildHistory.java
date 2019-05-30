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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
