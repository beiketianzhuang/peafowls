package com.lchen.walleapiclient.model;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

/**
 * @author : lchen
 * @date : 2019/6/18
 */
public class RequestHandlerKey {

    private final Set<String> pathMappings;
    private final Set<RequestMethod> supportedMethods;
    private final Set<? extends MediaType> supportedMediaTypes;
    private final Set<? extends MediaType> producibleMediaTypes;

    public RequestHandlerKey(
            Set<String> pathMappings,
            Set<RequestMethod> supportedMethods,
            Set<? extends MediaType> supportedMediaTypes,
            Set<? extends MediaType> producibleMediaTypes) {

        this.pathMappings = pathMappings;
        this.supportedMethods = supportedMethods;
        this.supportedMediaTypes = supportedMediaTypes;
        this.producibleMediaTypes = producibleMediaTypes;
    }

    public Set<String> getPathMappings() {
        return pathMappings;
    }

    public Set<RequestMethod> getSupportedMethods() {
        return supportedMethods;
    }

    public Set<? extends MediaType> getSupportedMediaTypes() {
        return supportedMediaTypes;
    }

    public Set<? extends MediaType> getProducibleMediaTypes() {
        return producibleMediaTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestHandlerKey that = (RequestHandlerKey) o;
        return Sets.symmetricDifference(pathMappings, that.pathMappings).isEmpty() &&
                Sets.symmetricDifference(supportedMethods, that.supportedMethods).isEmpty() &&
                Sets.symmetricDifference(supportedMediaTypes, that.supportedMediaTypes).isEmpty() &&
                Sets.symmetricDifference(producibleMediaTypes, that.producibleMediaTypes).isEmpty();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pathMappings, supportedMethods, supportedMediaTypes, producibleMediaTypes);
    }
}
