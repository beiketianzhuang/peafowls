package com.lchen.walleapiadmin.model.req;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpMethod;

/**
 * @author : lchen
 * @date : 2019/6/21
 */
@Data
@Builder
public class ApiDocumentReq {

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
