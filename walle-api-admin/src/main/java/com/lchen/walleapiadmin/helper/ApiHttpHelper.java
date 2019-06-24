package com.lchen.walleapiadmin.helper;

import com.lchen.walleapiadmin.model.ApiRunnerHistory;
import com.lchen.walleapiadmin.model.constants.HttpMethodBase;
import com.lchen.walleapiadmin.model.resp.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_MOVED_TEMPORARILY;
import static org.apache.http.HttpStatus.SC_OK;

@Slf4j
@Component
public class ApiHttpHelper implements ApiHttpConnection {

    private static final String REQUEST_ERROR_MESSAGE = "请求状态异常：%d";
    private static final String HEADER_LOCATION = "Location";

    @Override
    public void close() {
    }

    @Override
    public ApiResp call(HttpRequestBase requestBase) {
        ApiResp apiResp = new ApiResp();
        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
            requestBase.setConfig(requestConfig);
            HttpResponse response = client.execute(requestBase);
            HttpEntity entity = response.getEntity();
            apiResp = ofNullable(entity).map(e -> apiRespBuilder(e, response)).orElse(new ApiResp());
        } catch (IOException e) {
            log.error("执行http请求失败", e);
        } finally {
            closeAllConnection(requestBase, client);
        }
        return apiResp;
    }

    public HttpRequestBase buildHttpRequestBase(ApiRunnerHistory apiRunnerHistory) {
        return HttpMethodBase
                .valueOf(apiRunnerHistory.getMethod().name())
                .apply(apiRunnerHistory.getRequestUrl(), apiRunnerHistory.getRequestParams(), apiRunnerHistory.getRequestHeaders());
    }

    private ApiResp apiRespBuilder(HttpEntity entity, HttpResponse response) {
        String responseContent = EMPTY;
        try {
            if (response.getStatusLine().getStatusCode() != SC_OK) {
                responseContent = format(REQUEST_ERROR_MESSAGE, response.getStatusLine().getStatusCode());
                if (response.getStatusLine().getStatusCode() == SC_MOVED_TEMPORARILY && nonNull(response.getFirstHeader(HEADER_LOCATION))) {
                    responseContent += "；Redirect地址：" + response.getFirstHeader(HEADER_LOCATION).getValue();
                }
            }
            responseContent += EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
        } catch (Exception e) {
            log.error("获取返回信息失败", e);
        }
        return ApiResp.builder().message(responseContent).status(response.getStatusLine().getStatusCode()).build();
    }

    private void closeAllConnection(HttpRequestBase requestBase, CloseableHttpClient client) {
        ofNullable(requestBase).ifPresent(HttpRequestBase::releaseConnection);
        ofNullable(client).ifPresent(this::clientClose);
    }

    private void clientClose(CloseableHttpClient client) {
        try {
            client.close();
        } catch (IOException ignored) {
        }
    }
}