package com.lchen.walleapiadmin.helper;

import com.lchen.walleapiadmin.model.ApiRunnerHistory;
import com.lchen.walleapiadmin.model.constants.HttpMethodBase;
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

@Slf4j
@Component
public class ApiHttpHelper implements ApiHttpConnection {

    @Override
    public void close() {

    }

    public HttpRequestBase buildHttpRequestBase(ApiRunnerHistory apiRunnerHistory) {
        return HttpMethodBase
                .valueOf(apiRunnerHistory.getMethod().name())
                .apply(apiRunnerHistory.getRequestUrl(), apiRunnerHistory.getRequestParams(), apiRunnerHistory.getRequestHeaders());
    }

    @Override
    public String call(HttpRequestBase requestBase) {
        String responseContent = "";
        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
            requestBase.setConfig(requestConfig);
            HttpResponse response = client.execute(requestBase);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    responseContent = EntityUtils.toString(entity, "UTF-8");
                } else {
                    responseContent = "请求状态异常：" + response.getStatusLine().getStatusCode();
                    if (statusCode == 302 && response.getFirstHeader("Location") != null) {
                        responseContent += "；Redirect地址：" + response.getFirstHeader("Location").getValue();
                    }

                }
                EntityUtils.consume(entity);
            }
            log.info("http statusCode error, statusCode:" + response.getStatusLine().getStatusCode());
        } catch (Exception e) {
        } finally {
            closeConnection(requestBase, client);
        }

        return responseContent;
    }

    private void closeConnection(HttpRequestBase requestBase, CloseableHttpClient client) {
        if (requestBase != null) {
            requestBase.releaseConnection();
        }
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}