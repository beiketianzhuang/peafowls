package com.lchen.walleapiclient.model;


import java.util.List;


/**
 * @author : lchen
 * @date : 2019/6/20
 */
public class ResourceListing {
    private final String apiVersion;
    private final List<ApiListingReference> apis;
    private final List<SecurityScheme> securitySchemes;
    private final ApiInfo info;

    public ResourceListing(String apiVersion,
                           List<ApiListingReference> apis,
                           List<SecurityScheme> securitySchemes,
                           ApiInfo info) {

        this.apiVersion = apiVersion;
        this.apis = apis;
        this.securitySchemes = securitySchemes;
        this.info = info;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public List<ApiListingReference> getApis() {
        return apis;
    }

    public List<SecurityScheme> getSecuritySchemes() {
        return securitySchemes;
    }

    public ApiInfo getInfo() {
        return info;
    }
}
