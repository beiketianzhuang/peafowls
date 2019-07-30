package com.lchen.funnel.interceptor;

import com.lchen.funnel.config.RouterMappingProperties;
import com.lchen.funnel.filter.ResponseData;

public class NoOpPostForwardResponseInterceptor implements PostForwardResponseInterceptor {
    @Override
    public void intercept(ResponseData data, RouterMappingProperties mapping) {

    }
}
