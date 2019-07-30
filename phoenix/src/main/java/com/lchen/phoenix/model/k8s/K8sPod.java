package com.lchen.phoenix.model.k8s;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : lchen
 * @date : 2019/7/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class K8sPod {
    private Integer replicas;

    public String toMessage() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
