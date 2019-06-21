package com.lchen.walleapiadmin.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author : lchen
 * @date : 2019/6/21
 */
@Data
@Entity
@Builder
public class ApiGroup extends BaseDomain {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long contextId;
    private String group;
    private String groupName;
    private Integer order;
    private String description;

}
