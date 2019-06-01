package com.lchen.ccdeploy.utils;

import com.offbytwo.jenkins.model.BaseModel;
import lombok.Data;

/**
 * @author : lchen
 * @date : 2019/6/1
 */
@Data
public class BuildProgress extends BaseModel {
    private Integer executor;
}
