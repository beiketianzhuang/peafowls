package com.lchen.walleapiadmin.model.constants;

import com.lchen.walleapiadmin.model.RequestTag;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

@Slf4j
public enum HttpMethodBase {
    //post方法
    POST {
        @Override
        public HttpRequestBase apply(String url, List<RequestTag> params, List<RequestTag> headers) {
            HttpPost httpPost = new HttpPost(url);
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> formParams = newArrayList();
                for (RequestTag param : params) {
                    formParams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    log.error("", e);
                }
            }
            return builder(httpPost, headers);
        }
    },

    GET {
        @Override
        public HttpRequestBase apply(String url, List<RequestTag> params, List<RequestTag> headers) {
            HttpGet httpGet = new HttpGet(urlBuilder(url, params));
            return builder(httpGet, headers);
        }
    },

    DELETE {
        @Override
        public HttpRequestBase apply(String url, List<RequestTag> params, List<RequestTag> headers) {
            HttpDelete httpDelete = new HttpDelete(urlBuilder(url, params));
            return builder(httpDelete, headers);
        }
    },

    PUT {
        @Override
        public HttpRequestBase apply(String url, List<RequestTag> params, List<RequestTag> headers) {
            HttpPut httpPut = new HttpPut(urlBuilder(url, params));
            return builder(httpPut, headers);
        }
    },
    HEAD {
        @Override
        public HttpRequestBase apply(String url, List<RequestTag> params, List<RequestTag> headers) {
            HttpRequestBase requestBase = new HttpHead(urlBuilder(url, params));
            return builder(requestBase, headers);
        }
    };

    public abstract HttpRequestBase apply(String url, List<RequestTag> params, List<RequestTag> headers);

    protected HttpRequestBase builder(HttpRequestBase request, List<RequestTag> headers) {
        if (isNotEmpty(headers)) {
            for (RequestTag header : headers) {
                request.setHeader(header.getKey(), header.getValue());
            }
        }
        return request;
    }

    protected static String urlBuilder(String url, List<RequestTag> params) {
        StringBuilder finalUrl = new StringBuilder(url);
        if (isNotEmpty(params)) {
            finalUrl = new StringBuilder(url + "?");
            for (RequestTag param : params) {
                finalUrl.append(param.getKey()).append("=").append(param.getValue()).append("&");
            }
            finalUrl = new StringBuilder(finalUrl.substring(0, finalUrl.length() - 1));
        }
        return finalUrl.toString();
    }
}
