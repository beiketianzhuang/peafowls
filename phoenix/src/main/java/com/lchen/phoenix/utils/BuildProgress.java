package com.lchen.phoenix.utils;

import com.offbytwo.jenkins.model.BaseModel;
import lombok.Data;

/**
 * @author : lchen
 * @date : 2019/6/1
 */
@Data
public class BuildProgress extends BaseModel {

    private ProgressValue executor;

    @Data
    public static class ProgressValue {
        private Integer progress;
    }
}
