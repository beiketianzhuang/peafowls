package com.lchen.funnel.interceptor;

import com.lchen.funnel.config.RouterMappingProperties;
import com.lchen.funnel.filter.RequestData;

public class NoOpPreForwardRequestInterceptor implements PreForwardRequestInterceptor {
    @Override
    public void intercept(RequestData data, RouterMappingProperties mapping) {

    }
}
