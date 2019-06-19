package com.lchen.walleapiclient.handler;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Optional;
import com.lchen.walleapiclient.model.RequestHandlerKey;
import com.lchen.walleapiclient.model.ResolvedMethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author : lchen
 * @date : 2019/6/18
 */
public class ApiWebMvcRequestHandler implements ApiRequestHandler {
    private final RequestMappingInfo requestMapping;
    private final HandlerMethod handlerMethod;

    public ApiWebMvcRequestHandler(
            RequestMappingInfo requestMapping,
            HandlerMethod handlerMethod) {
        this.requestMapping = requestMapping;
        this.handlerMethod = handlerMethod;
    }

    @Override
    public ApiRequestHandler combine(ApiRequestHandler other) {
        return this;
    }

    @Override
    public boolean isAnnotatedWith(Class<? extends Annotation> annotation) {
        return null != AnnotationUtils.findAnnotation(handlerMethod.getMethod(), annotation);
    }

    @Override
    public PatternsRequestCondition getPatternsCondition() {
        return requestMapping.getPatternsCondition();
    }

    @Override
    public String groupName() {
        return controllerNameAsGroup(handlerMethod);
    }

    @Override
    public String getName() {
        return handlerMethod.getMethod().getName();
    }

    @Override
    public Set<RequestMethod> supportedMethods() {
        return requestMapping.getMethodsCondition().getMethods();
    }

    @Override
    public Set<? extends MediaType> produces() {
        return requestMapping.getProducesCondition().getProducibleMediaTypes();
    }

    @Override
    public Set<? extends MediaType> consumes() {
        return requestMapping.getConsumesCondition().getConsumableMediaTypes();
    }

    @Override
    public Set<NameValueExpression<String>> headers() {
        return requestMapping.getHeadersCondition().getExpressions();
    }

    @Override
    public Set<NameValueExpression<String>> params() {
        return requestMapping.getParamsCondition().getExpressions();
    }

    @Override
    public <T extends Annotation> Optional<T> findAnnotation(Class<T> annotation) {
        return Optional.fromNullable(AnnotationUtils.findAnnotation(handlerMethod.getMethod(), annotation));
    }

    @Override
    public RequestHandlerKey key() {
        return new RequestHandlerKey(
                requestMapping.getPatternsCondition().getPatterns(),
                requestMapping.getMethodsCondition().getMethods(),
                requestMapping.getConsumesCondition().getConsumableMediaTypes(),
                requestMapping.getProducesCondition().getProducibleMediaTypes());
    }

    @Override
    public List<ResolvedMethodParameter> getParameters() {
        HandlerMethodResolver handlerMethodResolver = new HandlerMethodResolver(new TypeResolver());
        return handlerMethodResolver.methodParameters(handlerMethod);
    }

    @Override
    public ResolvedType getReturnType() {
        HandlerMethodResolver handlerMethodResolver = new HandlerMethodResolver(new TypeResolver());
        return handlerMethodResolver.methodReturnType(handlerMethod);
    }

    @Override
    public <T extends Annotation> Optional<T> findControllerAnnotation(Class<T> annotation) {
        return Optional.fromNullable(AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), annotation));
    }

    public static String controllerNameAsGroup(HandlerMethod handlerMethod) {
        Class<?> controllerClass = handlerMethod.getBeanType();
        return splitCamelCase(controllerClass.getSimpleName(), "-")
                .replace("/", "")
                .toLowerCase();
    }

    public static String splitCamelCase(String s, String separator) {
        if (isNullOrEmpty(s)) {
            return "";
        }
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                separator
        );
    }
}
