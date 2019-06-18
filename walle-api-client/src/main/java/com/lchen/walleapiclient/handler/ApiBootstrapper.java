package com.lchen.walleapiclient.handler;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.lchen.walleapiclient.provider.ApiRequestHandlerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.google.common.collect.FluentIterable.from;
import static com.lchen.walleapiclient.builders.BuilderDefault.nullToEmptyList;

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
        ImmutableList<ApiRequestHandler> requestHandlers = from(nullToEmptyList(handlerProviders))
                .transformAndConcat(handlers())
                .toList();
        System.out.println(requestHandlers);
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

    private Function<ApiRequestHandlerProvider, ? extends Iterable<ApiRequestHandler>> handlers() {
        return (Function<ApiRequestHandlerProvider, Iterable<ApiRequestHandler>>) input -> input.requestHandlers();
    }

}
