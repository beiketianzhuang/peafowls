package com.lchen.walleapiadmin.model.resp;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpMethod;

import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static javax.persistence.EnumType.STRING;

/**
 * @author : lchen
 * @date : 2019/6/21
 */
@Data
@Builder
public class ApiDocumentResp {

    private Long id;
    private Long contextId;
    private Long groupId;
    private String apiName;
    private String url;
    private boolean status;
    private HttpMethod method;
    private String description;
    private String headers;

}
