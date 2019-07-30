package com.lchen.phoenix.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : lchen
 * @date : 2019/6/1
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeploymentBuild {
    private List<JenkinsBuild> jenkinsBuilds;
    private List<DeploymentResult> deploymentHistories;

    public String toMessage() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
