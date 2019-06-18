package com.lchen.walleapiclient.provider;


import com.lchen.walleapiclient.handler.ApiRequestHandler;

import java.util.List;

/**
 * @author : lchen
 * @date : 2019/6/18
 */
public interface RequestHandlerProvider {
    List<ApiRequestHandler> requestHandlers();
}