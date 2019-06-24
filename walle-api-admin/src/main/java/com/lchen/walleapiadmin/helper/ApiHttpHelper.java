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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.lchen.walleapiadmin.model.resp.ApiResp.apiRespBuilder;
import static java.util.Optional.ofNullable;

@Slf4j
@Component
public class ApiHttpHelper implements ApiHttpConnection {

    @Value("${api.request.socket.timeout:5000}")
    private Integer socketTimeout;
    @Value("${api.request.connect.timeout:100}")
    private Integer connectTimeout;

    @Override
    public void close() {
    }

    @Override
    public ApiResp call(HttpRequestBase requestBase) {
        ApiResp apiResp = new ApiResp();
        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
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