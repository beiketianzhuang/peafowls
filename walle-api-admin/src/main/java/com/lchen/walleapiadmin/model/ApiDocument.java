package com.lchen.walleapiadmin.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpMethod;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.EnumType.STRING;

/**
 * @author : lchen
 * @date : 2019/6/21
 */
@Data
@Entity
@Builder
public class ApiDocument extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long contextId;
    private Long groupId;
    private String apiName;
    private String url;
    private boolean status;
    @Enumerated(STRING)
    private HttpMethod method;
    private String description;
    private List<RequestTag> headers;


}
