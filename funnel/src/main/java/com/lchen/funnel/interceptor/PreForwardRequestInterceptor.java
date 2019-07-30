package com.lchen.funnel.interceptor;

import com.lchen.funnel.config.RouterMappingProperties;
import com.lchen.funnel.filter.RequestData;

public interface PreForwardRequestInterceptor {
    void intercept(RequestData data, RouterMappingProperties mapping);
}
