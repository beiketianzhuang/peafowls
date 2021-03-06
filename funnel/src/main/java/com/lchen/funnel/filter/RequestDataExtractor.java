package com.lchen.funnel.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import static java.util.Collections.list;
import static org.apache.commons.io.IOUtils.toByteArray;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class RequestDataExtractor {

    public byte[] extractBody(HttpServletRequest request) {
        try {
            return toByteArray(request.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }

    public HttpHeaders extractHttpHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            List<String> value = list(request.getHeaders(name));
            headers.put(name, value);
        }
        return headers;
    }

    public HttpMethod extractHttpMethod(HttpServletRequest request) {
        return HttpMethod.resolve(request.getMethod());
    }

    public String extractContext(HttpServletRequest request) {
        if (StringUtils.isBlank(request.getRequestURI())) {
            return EMPTY;
        }
        String[] urlArrays = StringUtils.split(request.getRequestURI(), "/");
        return urlArrays[0];
    }

    public String extractUri(HttpServletRequest request) {
        return request.getRequestURI() + getQuery(request);
    }

    public String extractHost(HttpServletRequest request) {
        return request.getServerName();
    }

    protected String getQuery(HttpServletRequest request) {
        return request.getQueryString() == null ? EMPTY : "?" + request.getQueryString();
    }
}
