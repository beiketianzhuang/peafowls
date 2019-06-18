package com.lchen.walleapiclient.provider;

import com.lchen.walleapiclient.handler.ApiRequestHandler;
import com.lchen.walleapiclient.provider.RequestHandlerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.RequestHandler;
import springfox.documentation.spring.web.WebMvcRequestHandler;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

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

        return null;
    }

    private Function<? super RequestMappingInfoHandlerMapping,
            Iterable<Map.Entry<RequestMappingInfo, HandlerMethod>>> toMappingEntries() {
        return (Function<RequestMappingInfoHandlerMapping, Iterable<Map.Entry<RequestMappingInfo, HandlerMethod>>>) input -> input.getHandlerMethods().entrySet();
    }

    private Function<Map.Entry<RequestMappingInfo, HandlerMethod>, RequestHandler> toRequestHandler() {
        return input -> new WebMvcRequestHandler(input.getKey(), input.getValue());
    }

}
