package com.lchen.walleapiclient.provider;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import com.lchen.walleapiclient.handler.ApiRequestHandler;
import com.lchen.walleapiclient.handler.ApiWebMvcRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.FluentIterable.from;
import static com.lchen.walleapiclient.builders.BuilderDefault.nullToEmptyList;

/**
 * @author : lchen
 * @date : 2019/6/18
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiRequestHandlerProvider implements RequestHandlerProvider {
    private final List<RequestMappingInfoHandlerMapping> handlerMappings;

    @Autowired
    public ApiRequestHandlerProvider(List<RequestMappingInfoHandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    @Override
    public List<ApiRequestHandler> requestHandlers() {
        return byPatternsCondition().sortedCopy(from(nullToEmptyList(handlerMappings))
                .transformAndConcat(toMappingEntries())
                .transform(toRequestHandler()));
    }

    public static Ordering<ApiRequestHandler> byPatternsCondition() {
        return Ordering.from(Comparator.comparing(apiRequestHandler -> patternsCondition(apiRequestHandler).toString()));
    }

    public static PatternsRequestCondition patternsCondition(ApiRequestHandler handler) {
        return handler.getPatternsCondition();
    }

    private com.google.common.base.Function<? super RequestMappingInfoHandlerMapping,
            Iterable<Map.Entry<RequestMappingInfo, HandlerMethod>>> toMappingEntries() {

        return (Function<RequestMappingInfoHandlerMapping, Iterable<Map.Entry<RequestMappingInfo, HandlerMethod>>>) input -> {
            assert input != null;
            return input.getHandlerMethods().entrySet();
        };
    }

    private com.google.common.base.Function<Map.Entry<RequestMappingInfo, HandlerMethod>, ApiRequestHandler> toRequestHandler() {
        return input -> {
            assert input != null;
            return new ApiWebMvcRequestHandler(input.getKey(), input.getValue());
        };
    }

}
