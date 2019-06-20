package com.lchen.walleapiclient.handler;

import com.lchen.walleapiclient.provider.ApiRequestHandlerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static com.lchen.walleapiclient.builders.BuilderDefault.nullToEmptyList;
import static java.util.stream.Collectors.toList;

/**
 * @author : lchen
 * @date : 2019/6/18
 */
@Component
public class ApiBootstrapper implements SmartLifecycle {

    private final List<ApiRequestHandlerProvider> handlerProviders;

    @Autowired
    public ApiBootstrapper(List<ApiRequestHandlerProvider> handlerProviders) {
        this.handlerProviders = handlerProviders;
    }

    @Override
    public void start() {
        List<ApiRequestHandler> apiRequestHandlers = nullToEmptyList(handlerProviders).stream()
                .map(ApiRequestHandlerProvider::requestHandlers)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    private void defaultContextBuilder() {
    }


}
