package com.lchen.walleapiadmin.model;

import com.lchen.walleapiadmin.model.constants.PreserveType;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.EnumType.STRING;

/**
 * @author : lchen
 * @date : 2019/6/21
 */
@Data
@Builder
public class ApiRunnerHistory extends BaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long apiId;
    @Enumerated(STRING)
    private HttpStatus status;
    private Integer statusCode;
    private List<RequestTag> requestParams;
    private HttpMethod method;
    private List<RequestTag> requestHeaders;
    private String requestUrl;
    @Enumerated(STRING)
    private PreserveType preserveType;
    private String responseType;
}
