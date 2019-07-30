package com.lchen.funnel.interceptor;

import com.lchen.funnel.config.RouterMappingProperties;
import com.lchen.funnel.filter.ResponseData;

public interface PostForwardResponseInterceptor {
    void intercept(ResponseData data, RouterMappingProperties mapping);
}
