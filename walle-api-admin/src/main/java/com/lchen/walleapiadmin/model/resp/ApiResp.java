package com.lchen.walleapiadmin.model.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import static java.lang.String.format;
import static java.nio.charset.Charset.defaultCharset;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_MOVED_TEMPORARILY;
import static org.apache.http.HttpStatus.SC_OK;

/**
 * @author : lchen
 * @date : 2019/6/24
 */
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResp {
    private int status;
    private String message;
    private String errorMessage;

    private static final String REQUEST_ERROR_MESSAGE = "请求状态异常：%d";
    private static final String HEADER_LOCATION = "Location";

    public static ApiResp apiRespBuilder(HttpEntity entity, HttpResponse response) {
        String responseContent = EMPTY;
        try {
            if (response.getStatusLine().getStatusCode() != SC_OK) {
                responseContent = format(REQUEST_ERROR_MESSAGE, response.getStatusLine().getStatusCode());
                if (response.getStatusLine().getStatusCode() == SC_MOVED_TEMPORARILY && nonNull(response.getFirstHeader(HEADER_LOCATION))) {
                    responseContent = responseContent.concat("；Redirect地址：" + response.getFirstHeader(HEADER_LOCATION).getValue());
                }
            }
            responseContent = responseContent.concat(EntityUtils.toString(entity, defaultCharset()));
            EntityUtils.consume(entity);
        } catch (Exception e) {
            log.error("获取返回信息失败", e);
        }
        return ApiResp.builder().message(responseContent).status(response.getStatusLine().getStatusCode()).build();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
